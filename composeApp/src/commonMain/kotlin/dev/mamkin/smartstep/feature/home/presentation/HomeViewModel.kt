package dev.mamkin.smartstep.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.smartstep.core.domain.model.Permission
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import dev.mamkin.smartstep.core.domain.service.PermissionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val permissionManager: PermissionManager
) : ViewModel() {

    private var isInitialized = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true

                viewModelScope.launch {
                    userProfileRepository.getStepGoal().collectLatest { savedStepGoal ->
                        _state.update { currentState ->
                            currentState.copy(currentStepGoal = savedStepGoal)
                        }
                    }
                }

                viewModelScope.launch {
                    permissionManager.requestInitialPermission(Permission.RUN)

                    permissionManager.isPermissionGranted(Permission.RUN)
                        .collectLatest { (isGranted, isPermanentlyDenied) ->
                            _state.update { currentState ->
                                currentState.copy(
                                    isRunPermissionDenied = !isGranted,
                                    runPermissionBottomSheet = if (isPermanentlyDenied) {
                                        RunPermissionBottomSheet.OPEN_SETTINGS
                                    } else {
                                        RunPermissionBottomSheet.ALLOW_ACCESS
                                    }
                                )
                            }
                        }
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnToggleExitDialogVisibility -> {
                toggleDialogVisibility()
            }

            HomeAction.OnDismissBackgroundProcessDialog -> {
                _state.update {
                    it.copy(
                        shouldDisplayBackgroundAccessDialog = false
                    )
                }
            }

            is HomeAction.OnNewStepGoalSet -> {
                setNewStepGoal(action.stepGoal)
                onAction(HomeAction.OnToggleStepGoalDialogVisibility)
            }

            HomeAction.OnToggleStepGoalDialogVisibility -> {
                _state.update {
                    it.copy(
                        shouldDisplayStepGoalDialog = !it.shouldDisplayStepGoalDialog
                    )
                }
            }

            HomeAction.OnAllowAccessRunPermissionClick -> {
                permissionManager.onRequestPermission(Permission.RUN)
            }
        }
    }

    private fun setNewStepGoal(stepGoal: Int) {
        viewModelScope.launch {
            userProfileRepository.setStepGoal(stepGoal)
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