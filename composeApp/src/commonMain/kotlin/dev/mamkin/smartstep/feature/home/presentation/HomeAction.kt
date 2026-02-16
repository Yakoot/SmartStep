package dev.mamkin.smartstep.feature.home.presentation

import dev.mamkin.smartstep.core.presentation.utils.Permission

sealed interface HomeAction {

    data class OnSheetTypeChanged(val sheetType: SheetType) : HomeAction
    data object OnToggleExitDialogVisibility : HomeAction
    data class OnNewStepGoalSet(val stepGoal: Int) : HomeAction

    data object OnScreenVisible : HomeAction
    data object OnAllowAccessClick: HomeAction
    data class OnPermissionRequestLaunched(val permission: Permission): HomeAction

    data object OnOpenAppSettingsClick: HomeAction
    data object OnBackgroundContinueClick : HomeAction

    data class OnNewPermissionResult(val isGranted: Boolean) : HomeAction
    data object OnResumed: HomeAction

}