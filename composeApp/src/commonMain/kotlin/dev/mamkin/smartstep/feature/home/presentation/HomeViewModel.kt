package dev.mamkin.smartstep.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val userProfileRepository: UserProfileRepository) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userProfileRepository.getStepGoal().collectLatest { savedStepGoal ->
                _state.update { currentState ->
                  currentState.copy(currentStepGoal = savedStepGoal)
                }
            }
        }
    }
    fun onAction(action: HomeAction) {
        when (action) {
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