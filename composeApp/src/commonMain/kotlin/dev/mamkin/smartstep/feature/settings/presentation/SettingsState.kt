package dev.mamkin.smartstep.feature.settings.presentation

data class SettingsState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)