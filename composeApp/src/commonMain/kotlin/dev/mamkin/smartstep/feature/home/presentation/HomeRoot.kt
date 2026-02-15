package dev.mamkin.smartstep.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.app.util.requestAppExit
import dev.mamkin.smartstep.core.presentation.components.MainTopBar
import dev.mamkin.smartstep.core.presentation.components.dialogs.SettingsDialog
import dev.mamkin.smartstep.core.presentation.components.layouts.AfterFirstPermissionDenialLayout
import dev.mamkin.smartstep.core.presentation.components.layouts.BackgroundAccessRecommendedLayout
import dev.mamkin.smartstep.core.presentation.components.layouts.ManualPermissionLayout
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.feature.home.presentation.components.StepGoalBottomSheet
import dev.mamkin.smartstep.feature.home.presentation.components.StepsCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.drawer_item_exit
import smartstep.composeapp.generated.resources.drawer_item_personal_settings
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
//                    viewModel.onAction(HomeAction.OnSheetTypeChanged(SheetType.AFTER_FIRST_DENIAL))
                            onNavigate(SmartStepGraph.ProfileSetupScreen(isInitialSetup = false))
                        }

                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(title = stringResource(Res.string.drawer_item_exit), onClick =
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
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        MainTopBar(onDrawerOpen = onDrawerOpen)
    }, modifier = modifier,
        containerColor = AppTheme.colors.backgroundMain
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            StepsCard(
                steps = 1000,
                goal = 5000,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (state.shouldDisplayExitDialog)
                SettingsDialog(onDismiss = {
                    onAction(HomeAction.OnToggleExitDialogVisibility)
                    requestAppExit()
                })

            when (state.sheetType) {
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
                    AfterFirstPermissionDenialLayout(sheetState = sheetState, onDismiss = onDismiss)
                }

                SheetType.MANUAL_PERMISSION -> {
                    ManualPermissionLayout(sheetState = sheetState, onDismiss = onDismiss)
                }

                SheetType.BACKGROUND_ACCESS -> {
                    BackgroundAccessRecommendedLayout(
                        sheetState = sheetState,
                        onDismiss = onDismiss
                    )
                }
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