package dev.mamkin.smartstep.feature.settings.presentation

import dev.mamkin.smartstep.core.domain.model.Gender

sealed interface SettingsAction {
    data object GenderButtonClicked: SettingsAction
    data object GenderDropdownDismissed: SettingsAction
    data class GenderSelected(val gender: Gender): SettingsAction
}