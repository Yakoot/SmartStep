package dev.mamkin.smartstep.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.mamkin.smartstep.app.MainViewModel
import dev.mamkin.smartstep.feature.home.presentation.HomeRoot
import dev.mamkin.smartstep.feature.home.presentation.HomeViewModel
import dev.mamkin.smartstep.feature.settings.presentation.SettingsRoot
import dev.mamkin.smartstep.feature.settings.presentation.SettingsViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(initialRoute: SmartStepGraph) {
    val navStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(SmartStepGraph.HomeScreen::class, SmartStepGraph.HomeScreen.serializer())
                    subclass(SmartStepGraph.ProfileSetupScreen::class, SmartStepGraph.ProfileSetupScreen.serializer())
                }
            }
        },
        initialRoute
    )

    val settingsViewModel: SettingsViewModel = koinViewModel()
    val homeViewModel: HomeViewModel = koinViewModel()

    NavDisplay(
        backStack = navStack,
        onBack = {
            navStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<SmartStepGraph.HomeScreen> {
                HomeRoot(
                    viewModel = homeViewModel,
                    onNavigate = { route -> navStack.add(route) })
            }

            entry<SmartStepGraph.ProfileSetupScreen> { args ->
                SettingsRoot(
                    viewModel = settingsViewModel,
                    onNavigateToHome = { navStack.add(SmartStepGraph.HomeScreen) },
                    isInitialSetup = args.isInitialSetup
                )
            }
        }
    )
}