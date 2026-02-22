package dev.mamkin.smartstep.feature.home.presentation

import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.core.presentation.model.WeeklyDay
import dev.mamkin.smartstep.feature.home.presentation.model.EditStepDate

data class HomeState(
    val todayStats: DailyStat? = null,
    val last7Days: List<WeeklyDay> = emptyList(),

    val shouldDisplayExitDialog: Boolean = false,
    val shouldRequestPermission: Boolean = false,
    val shouldRequestBackgroundAccess: Boolean = false,
    val isPhysicalActivityPermissionGranted: Boolean = false,

    val currentStepGoal: Int? = null,

    val editStepsDate: EditStepDate = EditStepDate.today(),
    val editSteps: Int = 0,

    val isTrackingPaused: Boolean = false,

    val activeSheet: SheetType = SheetType.NONE,
)


enum class SheetType {
    NONE, STEP_GOAL, EDIT_STEPS, RESET, AFTER_FIRST_DENIAL, MANUAL_PERMISSION, BACKGROUND_ACCESS
}
