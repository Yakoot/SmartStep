package dev.mamkin.smartstep.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SmartStepGraph {

    @Serializable
    data class ProfileSetupScreen(
        val isInitialSetup: Boolean = true
    ): SmartStepGraph

    @Serializable
    data object HomeScreen: SmartStepGraph

    @Serializable
    data object PersonalSettingsScreen: SmartStepGraph

}