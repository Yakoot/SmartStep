package dev.mamkin.smartstep.feature.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.app.util.requestAppExit
import dev.mamkin.smartstep.core.presentation.components.MainTopBar
import dev.mamkin.smartstep.core.presentation.components.dialogs.SettingsDialog
import dev.mamkin.smartstep.core.presentation.components.layouts.AfterFirstPermissionDenialLayout
import dev.mamkin.smartstep.core.presentation.components.layouts.BackgroundAccessRecommendedLayout
import dev.mamkin.smartstep.core.presentation.components.layouts.ManualPermissionLayout
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.core.presentation.utils.Permission
import dev.mamkin.smartstep.core.presentation.utils.rememberNewPermissionLauncher
import dev.mamkin.smartstep.feature.home.presentation.components.DailyAverageCard
import dev.mamkin.smartstep.feature.home.presentation.components.EditStepsDialog
import dev.mamkin.smartstep.feature.home.presentation.components.ResetStepsDialog
import dev.mamkin.smartstep.feature.home.presentation.components.SelectDateDialog
import dev.mamkin.smartstep.feature.home.presentation.components.StepGoalBottomSheet
import dev.mamkin.smartstep.feature.home.presentation.components.StepsCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.drawer_item_edit_steps
import smartstep.composeapp.generated.resources.drawer_item_exit
import smartstep.composeapp.generated.resources.drawer_item_personal_settings
import smartstep.composeapp.generated.resources.drawer_item_reset_today_steps
import smartstep.composeapp.generated.resources.drawer_item_step_goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoot(
    onNavigate: (SmartStepGraph) -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    val state by viewModel.state.collectAsStateWithLifecycle()

    val closeDrawerAndRun: (() -> Unit) -> () -> Unit = { action ->
        {
            action()
            scope.launch { drawerState.close() }
        }
    }

    val permissionLauncherNew = rememberNewPermissionLauncher { isGranted ->
        viewModel.onAction(HomeAction.OnNewPermissionResult(isGranted))
    }

    // 2. This effect runs ONCE to start the entire flow.
    LaunchedEffect(Unit) {
        viewModel.onAction(HomeAction.OnScreenVisible)
    }

    // 3. This effect OBEYS the ViewModel's command to launch the dialog.
    LaunchedEffect(state.shouldRequestPermission) {
        if (state.shouldRequestPermission) {
            // The ViewModel has commanded us to ask, so we ask.
            permissionLauncherNew.launch(Permission.PhysicalActivityMotionSensors)
            // Tell the ViewModel we've obeyed the command.
            viewModel.onAction(HomeAction.OnPermissionRequestLaunched(Permission.PhysicalActivityMotionSensors))
        }
    }

    // ADD THIS NEW, CORRECT LAUNCHEDEFFECT FOR LIFECYCLE EVENTS
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // Tell the ViewModel the app has returned to the foreground
                viewModel.onAction(HomeAction.OnResumed)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        // Clean up the observer when the composable is disposed
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    /*   val permissionLauncher =
            rememberPermissionLauncher(permission = Permission.PhysicalActivityMotionSensors)


        if (state.shouldRequestPermission) {
            viewModel.onAction(HomeAction.OnSheetTypeChanged(SheetType.MANUAL_PERMISSION))
        }
        LaunchedEffect(
            lifecycleOwner,
                    state.isPhysicalActivityPermissionGranted,
            state.shouldRequestPermission
        ) {
              permissionLauncher.launch { isGranted ->
            val ss = 123
                }

        }*/



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_step_goal),
                    color = AppTheme.colors.textPrimary,
                    onClick = closeDrawerAndRun {
                        viewModel.onAction(HomeAction.OnSheetTypeChanged(SheetType.STEP_GOAL))
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_personal_settings),
                    color = AppTheme.colors.textPrimary,
                    onClick = closeDrawerAndRun {
                        onNavigate(SmartStepGraph.ProfileSetupScreen(isInitialSetup = false))
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_edit_steps),
                    color = AppTheme.colors.textPrimary,
                    onClick = closeDrawerAndRun {
                        viewModel.onAction(HomeAction.OnEditStepsClick)
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_reset_today_steps),
                    color = AppTheme.colors.textPrimary,
                    onClick = closeDrawerAndRun {
                        viewModel.onAction(HomeAction.OnResetStepsClick)
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_exit), onClick =
                        closeDrawerAndRun {
                            viewModel.onAction(HomeAction.OnToggleExitDialogVisibility)
                        }
                )

            }
        },
        modifier = modifier
    ) {

        HomeScreen(
            state = state,
            sheetState = sheetState,
            onDrawerOpen = {
                scope.launch { drawerState.open() }
            },
            onAction = viewModel::onAction,
            onDismiss = {
                viewModel.onAction(HomeAction.OnSheetTypeChanged(SheetType.NONE))
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    sheetState: SheetState,
    onDrawerOpen: () -> Unit,
    onDismiss: () -> Unit,
    onAction: (HomeAction) -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopBar(onDrawerOpen = onDrawerOpen)
        },
        containerColor = AppTheme.colors.backgroundMain
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.todayStats?.let {
                    StepsCard(
                        stats = it,
                        isTrackingPaused = state.isTrackingPaused,
                        goal = state.currentStepGoal ?: 5000,
                        onAction = onAction,
                        modifier = Modifier
                            .widthIn(max = 394.dp)
                            .padding(horizontal = 16.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                DailyAverageCard(
                    days = state.last7Days,
                    goalSteps = state.currentStepGoal ?: 5000,
                    modifier = Modifier
                        .widthIn(max = 394.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            if (state.shouldDisplayExitDialog) {
                SettingsDialog(onDismiss = {
                    onAction(HomeAction.OnToggleExitDialogVisibility)
                    requestAppExit()
                })
            }

            when (state.activeSheet) {
                SheetType.NONE -> {
                    LaunchedEffect(sheetState) {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        }
                    }
                }

                SheetType.STEP_GOAL -> {

                    StepGoalBottomSheet(
                        stepGoal = state.currentStepGoal,
                        sheetState = sheetState,
                        onDismiss = onDismiss,
                        onSave = { newStepGoal ->
                            onAction(HomeAction.OnNewStepGoalSet(newStepGoal))
                        })

                }

                SheetType.AFTER_FIRST_DENIAL -> {
                    AfterFirstPermissionDenialLayout(
                        sheetState = sheetState,
                        onDismiss = onDismiss,
                        onConfirm = {
                            onAction(HomeAction.OnAllowAccessClick)
                        }
                    )
                }

                SheetType.MANUAL_PERMISSION -> {
                    ManualPermissionLayout(
                        sheetState = sheetState,
                        onDismiss = onDismiss,
                        onConfirm = {
                            onAction(HomeAction.OnSheetTypeChanged(SheetType.NONE))
                            onAction(HomeAction.OnOpenAppSettingsClick)

                        })
                }

                SheetType.BACKGROUND_ACCESS -> {
                    BackgroundAccessRecommendedLayout(
                        sheetState = sheetState,
                        onDismiss = onDismiss,
                        onConfirm = {
                            onAction(HomeAction.OnBackgroundContinueClick)
                        }
                    )
                }

                SheetType.RESET -> {
                    ResetStepsDialog(
                        onDismissRequest = {
                            onAction(HomeAction.OnDismissResetDialog)
                        },
                        onReset = {
                            onAction(HomeAction.OnResetStepsConfirm)
                        }
                    )
                }

                SheetType.EDIT_STEPS -> {
                    EditStepsDialog(
                        selectedDate = state.editStepsDate,
                        selectedSteps = state.editSteps,
                        onDismissRequest = {
                            onAction(HomeAction.OnStepEditCancelClick)
                        },
                        onDateClick = {
                            onAction(HomeAction.OnStepEditDateClick)
                        },
                        onSaveClick = {
                            onAction(HomeAction.OnStepEditSaveClick)
                        },
                        onStepsChange = {
                            onAction(HomeAction.OnStepEditStepsChange(it.toIntOrNull() ?: 0))
                        }
                    )
                }
            }

            if (state.shouldRequestBackgroundAccess) {
                SelectDateDialog(
                    initialData = state.editStepsDate,
                    onSave = {
                        onAction(HomeAction.OnStepEditDateChange(it))
                    },
                    onDismissRequest = {
                        onAction(HomeAction.OnDatePickerCancelClick)
                    }
                )
            }
        }
    }

}

@Composable
fun DrawerItem(
    title: String,
    color: Color = Color.Unspecified,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationDrawerItem(
        label = {
            Text(
                title,
                style = MaterialTheme.typography.bodyLargeMedium,
                color = color
            )
        },
        selected = false,
        onClick = onClick,
        modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomePreview() {
    SmartStepTheme {
        HomeScreen(
            state = HomeState(),
            sheetState = rememberModalBottomSheetState(),
            onDrawerOpen = {

            },
            onDismiss = {

            },
            onAction = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 1280)
@Composable
fun WideHomePreview() {
    SmartStepTheme {
        HomeScreen(
            state = HomeState(),
            sheetState = rememberModalBottomSheetState(),
            onDrawerOpen = {

            },
            onDismiss = {

            },
            onAction = {}
        )
    }
}