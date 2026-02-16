package dev.mamkin.smartstep.feature.home.presentation

data class HomeState(
    val runPermissionBottomSheet: RunPermissionBottomSheet = RunPermissionBottomSheet.ALLOW_ACCESS,
    val shouldDisplayExitDialog: Boolean = false,
    val shouldDisplayStepGoalDialog: Boolean = false,
    val shouldDisplayBackgroundAccessDialog: Boolean = false,
    val currentSteps : Int = 0,
    val currentStepGoal : Int? = null,
    val isRunPermissionDenied: Boolean = false,
)


enum class RunPermissionBottomSheet {
    ALLOW_ACCESS,
    OPEN_SETTINGS
}
