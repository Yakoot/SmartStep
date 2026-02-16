package dev.mamkin.smartstep.feature.home.presentation

sealed interface HomeAction {

    data class OnSheetTypeChanged(val sheetType: SheetType) : HomeAction
    data object OnToggleExitDialogVisibility : HomeAction
    data class OnNewStepGoalSet(val stepGoal: Int): HomeAction
}