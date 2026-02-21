package dev.mamkin.smartstep.feature.home.presentation

import dev.mamkin.smartstep.feature.home.presentation.model.EditStepDate

data class HomeState(
    val sheetType: SheetType = SheetType.NONE,
    val shouldDisplayExitDialog: Boolean = false,
    val currentSteps: Int = 0,
    val currentStepGoal: Int? = null,
    val isPhysicalActivityPermissionGranted: Boolean = false,
    val shouldRequestPermission: Boolean = false,
    val editStepsDate: EditStepDate = EditStepDate.today(),
    val editSteps: Int = 0,
    val isDatePickerDialogVisible: Boolean = false
)


enum class SheetType {
    NONE, STEP_GOAL, EDIT_STEPS, RESET, AFTER_FIRST_DENIAL, MANUAL_PERMISSION, BACKGROUND_ACCESS
}
