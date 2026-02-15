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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.app.util.requestAppExit
import dev.mamkin.smartstep.core.presentation.components.MainTopBar
import dev.mamkin.smartstep.core.presentation.components.dialogs.SettingsDialog
import dev.mamkin.smartstep.core.presentation.components.layouts.AfterFirstPermissionDenialLayout
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.feature.home.presentation.components.StepsCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.drawer_item_exit
import smartstep.composeapp.generated.resources.drawer_item_personal_settings
import smartstep.composeapp.generated.resources.drawer_item_step_goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoot(onNavigate: (SmartStepGraph) -> Unit, modifier: Modifier = Modifier) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var shouldDisplayExitDialog by remember {
        mutableStateOf(false)
    }
    var shouldShowPermissionSheet by remember { mutableStateOf(true) }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                DrawerItem(
                    title = stringResource(Res.string.drawer_item_step_goal),
                    color = AppTheme.colors.textPrimary
                ) {
                    scope.launch { drawerState.close() }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))


                DrawerItem(
                    title = stringResource(Res.string.drawer_item_personal_settings),
                    color = AppTheme.colors.textPrimary
                ) {
                    onNavigate(SmartStepGraph.PersonalSettingsScreen)
                    scope.launch { drawerState.close() }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                DrawerItem(title = stringResource(Res.string.drawer_item_exit)) {
                    shouldDisplayExitDialog = true
                    scope.launch { drawerState.close() }
                }

            }
        },
        modifier = modifier
    ) {
        Scaffold(
            topBar = {
                MainTopBar(onDrawerOpen = {
                    scope.launch { drawerState.open() }
                })
            },
            containerColor = AppTheme.colors.backgroundMain
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                StepsCard(
                    steps = 1000,
                    goal = 5000,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                if (shouldDisplayExitDialog)
                    SettingsDialog(onDismiss = {
                        shouldDisplayExitDialog = false
                        requestAppExit()
                    })

                if (shouldShowPermissionSheet)
                    AfterFirstPermissionDenialLayout(sheetState = sheetState, onDismiss = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            shouldShowPermissionSheet = false
                        }
                    })
            }
        }
    }
}

@Composable
fun DrawerItem(title: String, color: Color = Color.Unspecified, onClick: () -> Unit) {
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
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}