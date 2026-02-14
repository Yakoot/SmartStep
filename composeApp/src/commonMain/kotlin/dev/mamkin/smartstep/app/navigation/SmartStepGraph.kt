package dev.mamkin.smartstep.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SmartStepGraph {

    @Serializable
    data object ProfileSetupScreen: SmartStepGraph

    @Serializable
    data object HomeScreen: SmartStepGraph

    @Serializable
    data object PersonalSettingsScreen: SmartStepGraph

}