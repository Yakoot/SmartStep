package dev.mamkin.smartstep.feature.home.presentation

import dev.mamkin.smartstep.core.presentation.utils.Permission
import dev.mamkin.smartstep.feature.home.presentation.model.EditStepDate

sealed interface HomeAction {

    data object OnResetStepsClick: HomeAction
    data object OnResetStepsConfirm: HomeAction
    data object OnDismissResetDialog: HomeAction
    data object OnEditSteps: HomeAction

    data class OnStepEditStepsChange(val steps: Int) : HomeAction
    data class OnStepEditDateChange(val date: EditStepDate) : HomeAction
    data object OnStepEditDateClick : HomeAction
    data object OnStepEditSaveClick : HomeAction
    data object OnStepEditCancelClick : HomeAction
    data object OnDatePickerCancelClick : HomeAction

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