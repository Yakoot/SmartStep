package dev.mamkin.smartstep

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mamkin.smartstep.app.MainViewModel
import dev.mamkin.smartstep.app.di.appModule
import dev.mamkin.smartstep.app.di.platformModule
import dev.mamkin.smartstep.app.navigation.AppNavigation
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration


@Composable
fun App(initialRoute: SmartStepGraph) {
    SmartStepTheme {
        AppNavigation(initialRoute = initialRoute)
    }
}