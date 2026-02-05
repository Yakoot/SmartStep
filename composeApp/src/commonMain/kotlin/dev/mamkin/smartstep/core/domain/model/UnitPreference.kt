package dev.mamkin.smartstep.core.domain.model

enum class UnitPreference {
    CM,
    FT;

    companion object {
        fun fromName(name: String?): UnitPreference {
            return runCatching { valueOf(name?.uppercase() ?: "") }
                .getOrDefault(CM)
        }
    }
}