package dev.mamkin.smartstep

import androidx.compose.runtime.Composable
import dev.mamkin.smartstep.app.navigation.AppNavigation
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme


@Composable
fun App(initialRoute: SmartStepGraph) {
    SmartStepTheme {
        AppNavigation(initialRoute = initialRoute)
    }
}