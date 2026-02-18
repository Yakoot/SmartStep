package dev.mamkin.smartstep.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import dev.mamkin.smartstep.core.presentation.utils.Permission
import dev.mamkin.smartstep.core.presentation.utils.PermissionManager
import dev.mamkin.smartstep.core.presentation.utils.PermissionStatus
import dev.mamkin.smartstep.core.presentation.utils.PlatformActionManager
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive

class HomeViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val permissionManager: PermissionManager,
    val platformActionManager: PlatformActionManager,
    private val stepTracker: StepTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                userProfileRepository.getStepGoal().collectLatest { savedStepGoal ->
                    _state.update { currentState ->
                        currentState.copy(currentStepGoal = savedStepGoal)
                    }
                }
            }

            launch {
                stepTracker.trackSteps().collectLatest { steps ->
                    _state.update { currentState ->
                        currentState.copy(currentSteps = steps)
                    }
                }
            }
        }
    }


    private suspend fun checkPermissionStatusOnResume() {

        val status = permissionManager.getPermissionStatus(Permission.PhysicalActivityMotionSensors)

        val wasGrantedBefore = _state.value.isPhysicalActivityPermissionGranted

        if (status == PermissionStatus.GRANTED && !wasGrantedBefore) {

            _state.update {
                it.copy(
                    isPhysicalActivityPermissionGranted = true,
                    sheetType = SheetType.BACKGROUND_ACCESS
                )
            }
        } else if (status != PermissionStatus.GRANTED && wasGrantedBefore) {
            _state.update { it.copy(isPhysicalActivityPermissionGranted = false) }
        }

    }

    private suspend fun checkInitialPermissionStatus() {
        val status = permissionManager.getPermissionStatus(Permission.PhysicalActivityMotionSensors)
        when (status) {
            PermissionStatus.GRANTED -> {
                _state.update { it.copy(isPhysicalActivityPermissionGranted = true) }
                platformActionManager.startStepTrackingService()
            }
            PermissionStatus.NOT_DETERMINED -> _state.update { it.copy(shouldRequestPermission = true) }
            PermissionStatus.DENIED -> _state.update { it.copy(sheetType = SheetType.AFTER_FIRST_DENIAL) }
            PermissionStatus.PERMANENTLY_DENIED -> _state.update { it.copy(sheetType = SheetType.MANUAL_PERMISSION) }
        }
    }


    private suspend fun handlePermissionDialogResult(isGranted: Boolean) {
        if (isGranted) {
            platformActionManager.startStepTrackingService()
            _state.update {
                it.copy(
                    isPhysicalActivityPermissionGranted = true,
                    sheetType = SheetType.BACKGROUND_ACCESS
                )
            }
        } else {
            delay(100)
            val newStatus =
                permissionManager.getPermissionStatus(Permission.PhysicalActivityMotionSensors)
            Logger.e("NEW STATUS $newStatus")
            if (newStatus == PermissionStatus.PERMANENTLY_DENIED) {
                _state.update { it.copy(sheetType = SheetType.MANUAL_PERMISSION) }
            } else {
                _state.update { it.copy(sheetType = SheetType.AFTER_FIRST_DENIAL) }
            }
        }
    }


    fun onAction(action: HomeAction) {
        when (action) {

            HomeAction.OnResumed -> {
                viewModelScope.launch {
                    checkPermissionStatusOnResume()
                }
            }

            HomeAction.OnScreenVisible -> {
                viewModelScope.launch {
                    checkInitialPermissionStatus()
                }
            }

            is HomeAction.OnPermissionRequestLaunched -> {
                viewModelScope.launch {
                    permissionManager.setPermissionRequested(Permission.PhysicalActivityMotionSensors)
                }
                _state.update { it.copy(shouldRequestPermission = false) }
            }

            is HomeAction.OnNewPermissionResult -> {
                viewModelScope.launch {
                    handlePermissionDialogResult(action.isGranted)
                }
            }

            HomeAction.OnAllowAccessClick -> {
                viewModelScope.launch {
                    handleAllowAccessClick()
                }
            }

            HomeAction.OnOpenAppSettingsClick -> {
                platformActionManager.openAppSettings()
            }

            is HomeAction.OnSheetTypeChanged -> {
                updateSheetType(sheetType = action.sheetType)
            }

            HomeAction.OnToggleExitDialogVisibility -> {
                toggleDialogVisibility()
            }

            is HomeAction.OnNewStepGoalSet -> {
                setNewStepGoal(action.stepGoal)
                updateSheetType(sheetType = SheetType.NONE)
            }

            HomeAction.OnBackgroundContinueClick -> {
                platformActionManager.requestIgnoreBatteryOptimizations()
                updateSheetType(SheetType.NONE)
            }

        }
    }

    private suspend fun handleAllowAccessClick() {
        val status = permissionManager.getPermissionStatus(Permission.PhysicalActivityMotionSensors)
        Logger.e("ACCESS STATUS $status")
        when (status) {
            PermissionStatus.DENIED, PermissionStatus.NOT_DETERMINED -> {
                _state.update {
                    it.copy(
                        shouldRequestPermission = true,
                        sheetType = SheetType.NONE
                    )
                }
            }
            /*  PermissionStatus.DENIED, PermissionStatus.NOT_DETERMINED -> {
                  _state.update { it.copy(shouldRequestPermission = true, sheetType = SheetType.NONE) }
              }*/
            PermissionStatus.PERMANENTLY_DENIED -> {
                _state.update { it.copy(sheetType = SheetType.MANUAL_PERMISSION) }
            }

            PermissionStatus.GRANTED -> {
                _state.update {
                    it.copy(
                        isPhysicalActivityPermissionGranted = true,
                        sheetType = SheetType.NONE
                    )
                }
            }
        }
    }

    private fun setNewStepGoal(stepGoal: Int) {
        viewModelScope.launch {
            userProfileRepository.setStepGoal(stepGoal)
        }
    }


    private fun updateSheetType(sheetType: SheetType) {
        _state.update { currentState ->
            currentState.copy(
                sheetType = sheetType
            )
        }
    }

    private fun toggleDialogVisibility() {
        _state.update { currentState ->
            currentState.copy(
                shouldDisplayExitDialog = !currentState.shouldDisplayExitDialog
            )
        }
    }
}