package dev.mamkin.smartstep.feature.settings.presentation

import dev.mamkin.smartstep.core.domain.model.Gender

data class SettingsState(
    val selectedGender: Gender = Gender.Female,
    val genderDropdownExpanded: Boolean = false
)