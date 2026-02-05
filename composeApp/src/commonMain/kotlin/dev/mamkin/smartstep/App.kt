package dev.mamkin.smartstep

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mamkin.smartstep.app.navigation.AppNavigation
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme

@Composable
@Preview
fun App() {
    SmartStepTheme {
        AppNavigation()
    }
}