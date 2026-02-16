package dev.mamkin.smartstep.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface SmartStepGraph: NavKey {

    @Serializable
    data class ProfileSetupScreen(
        val isInitialSetup: Boolean = true
    ): SmartStepGraph

    @Serializable
    data object HomeScreen: SmartStepGraph

    @Serializable
    data object PersonalSettingsScreen: SmartStepGraph

}