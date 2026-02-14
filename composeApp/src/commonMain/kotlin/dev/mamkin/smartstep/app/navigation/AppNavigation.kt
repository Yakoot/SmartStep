package dev.mamkin.smartstep.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mamkin.smartstep.feature.home.presentation.HomeRoot
import dev.mamkin.smartstep.feature.settings.presentation.SettingsRoot

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SmartStepGraph.ProfileSetupScreen
    ) {
        composable<SmartStepGraph.HomeScreen> {
            HomeRoot(onNavigate = { route -> navController.navigate(route)})
        }

        composable<SmartStepGraph.ProfileSetupScreen> {
            SettingsRoot(onNavigateToHome = { navController.navigate(SmartStepGraph.HomeScreen) })
        }

        composable<SmartStepGraph.PersonalSettingsScreen>{
            Box(Modifier.fillMaxSize().background(Color.Green))
        }

    }
}