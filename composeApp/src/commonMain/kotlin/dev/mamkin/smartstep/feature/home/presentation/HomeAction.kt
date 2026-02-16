package dev.mamkin.smartstep.feature.home.presentation

sealed interface HomeAction {
    data object OnToggleExitDialogVisibility : HomeAction
    data object OnToggleStepGoalDialogVisibility : HomeAction
    data object OnDismissBackgroundProcessDialog : HomeAction
    data object OnAllowAccessRunPermissionClick : HomeAction
    data class OnNewStepGoalSet(val stepGoal: Int) : HomeAction
}