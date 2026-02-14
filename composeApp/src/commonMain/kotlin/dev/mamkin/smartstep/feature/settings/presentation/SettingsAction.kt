package dev.mamkin.smartstep.feature.settings.presentation

import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.feature.settings.presentation.components.HeightPickerAction
import dev.mamkin.smartstep.feature.settings.presentation.components.WeightPickerAction

sealed interface SettingsAction {
    data object GenderButtonClicked: SettingsAction
    data object GenderDropdownDismissed: SettingsAction
    data class GenderSelected(val gender: Gender): SettingsAction
    data object HeightButtonClicked: SettingsAction
    data class HeightPicker(val action: HeightPickerAction): SettingsAction
    data object WeightButtonClicked: SettingsAction
    data class WeightPicker(val action: WeightPickerAction): SettingsAction
    data object StartClicked: SettingsAction
}