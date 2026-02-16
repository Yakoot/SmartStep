package dev.mamkin.smartstep.feature.home.presentation

data class HomeState(
    val sheetType: SheetType = SheetType.NONE,
    val shouldDisplayExitDialog: Boolean = false,
    val currentStepGoal : Int? = null,
    val isPhysicalActivityPermissionGranted: Boolean = false,
    val shouldRequestPermission: Boolean = false
)


enum class SheetType {
    NONE, STEP_GOAL, AFTER_FIRST_DENIAL, MANUAL_PERMISSION, BACKGROUND_ACCESS
}
