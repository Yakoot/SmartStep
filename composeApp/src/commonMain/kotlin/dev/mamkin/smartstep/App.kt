package dev.mamkin.smartstep

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mamkin.smartstep.app.di.appModule
import dev.mamkin.smartstep.app.di.platformModule
import dev.mamkin.smartstep.app.navigation.AppNavigation
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun App(platformConfiguration: KoinAppDeclaration = {}) {
    KoinApplication(
        application = {
            platformConfiguration()
            modules(appModule, platformModule)
        }
    ) {
        SmartStepTheme {
            AppNavigation()
        }
    }
}

@Composable
@Preview
private fun AppPreview() {
    App()
}