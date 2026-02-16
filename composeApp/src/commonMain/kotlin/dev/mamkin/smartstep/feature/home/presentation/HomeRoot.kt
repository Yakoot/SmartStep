package dev.mamkin.smartstep.feature.home.presentation

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val closeDrawerAndRun: (() -> Unit) -> () -> Unit = { action ->
        {
            action()
            scope.launch { drawerState.close() }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                closeDrawerAndRun = closeDrawerAndRun,
                onAction = viewModel::onAction,
                onNavigate = onNavigate
            )
        },
        modifier = modifier
    ) {
        HomeScreen(
            state = state,
            onDrawerOpen = {
                scope.launch { drawerState.open() }
            },
            onAction = viewModel::onAction,
        )

        BottomSheets(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}

@Composable
private fun DrawerContent(
    closeDrawerAndRun: (() -> Unit) -> () -> Unit,
    onAction: (HomeAction) -> Unit,
    onNavigate: (SmartStepGraph) -> Unit
) {
    ModalDrawerSheet {
        DrawerItem(
            title = stringResource(Res.string.drawer_item_step_goal),
            color = AppTheme.colors.textPrimary,
            onClick = closeDrawerAndRun {
                onAction(HomeAction.OnToggleStepGoalDialogVisibility)
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

        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        DrawerItem(
            title = stringResource(Res.string.drawer_item_exit),
            onClick = closeDrawerAndRun {
                onAction(HomeAction.OnToggleExitDialogVisibility)
            }
        )

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheets(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (state.shouldDisplayExitDialog) {
        SettingsDialog(
            onDismiss = {
                onAction(HomeAction.OnDismissBackgroundProcessDialog)
            }
        )
    }

    if (state.shouldDisplayStepGoalDialog) {
        StepGoalBottomSheet(
            stepGoal = state.currentStepGoal,
            sheetState = sheetState,
            onDismiss = {
                onAction(HomeAction.OnToggleStepGoalDialogVisibility)
            },
            onSave = { newStepGoal ->
                onAction(HomeAction.OnNewStepGoalSet(newStepGoal))
            }
        )
    }

    if (state.isRunPermissionDenied) {
        when (state.runPermissionBottomSheet) {
            RunPermissionBottomSheet.ALLOW_ACCESS -> {
                AfterFirstPermissionDenialLayout(
                    sheetState = sheetState,
                    onAllowAccessClick = {
                        onAction(HomeAction.OnAllowAccessRunPermissionClick)
                    }
                )
            }

            RunPermissionBottomSheet.OPEN_SETTINGS -> {
                ManualPermissionLayout(
                    sheetState = sheetState,
                    onOpenSettingsClick = {
                        onAction(HomeAction.OnAllowAccessRunPermissionClick)
                    }
                )
            }
        }
    }

    if (state.shouldDisplayBackgroundAccessDialog) {
        BackgroundAccessRecommendedLayout(
            sheetState = sheetState,
            onAllowAccessClick = {

            },
            onDismiss = {
                onAction(HomeAction.OnDismissBackgroundProcessDialog)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onDrawerOpen: () -> Unit,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MainTopBar(onDrawerOpen = onDrawerOpen)
        },
        modifier = modifier,
        containerColor = AppTheme.colors.backgroundMain
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            state.currentStepGoal?.let { goal ->
                StepsCard(
                    steps = state.currentSteps,
                    goal = goal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
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