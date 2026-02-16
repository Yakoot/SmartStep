package dev.mamkin.smartstep.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.mamkin.smartstep.feature.home.presentation.HomeRoot
import dev.mamkin.smartstep.feature.home.presentation.HomeViewModel
import dev.mamkin.smartstep.feature.settings.presentation.SettingsRoot
import dev.mamkin.smartstep.feature.settings.presentation.SettingsViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation() {
    val navStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(SmartStepGraph.HomeScreen::class, SmartStepGraph.HomeScreen.serializer())
                    subclass(SmartStepGraph.ProfileSetupScreen::class, SmartStepGraph.ProfileSetupScreen.serializer())
                    subclass(SmartStepGraph.PersonalSettingsScreen::class, SmartStepGraph.PersonalSettingsScreen.serializer())
                }
            }
        },
        SmartStepGraph.ProfileSetupScreen()
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

            entry<SmartStepGraph.PersonalSettingsScreen> {
                Box(Modifier.fillMaxSize().background(Color.Green))
            }
        }
    )
}