package dev.mamkin.smartstep.core.domain.model

enum class UnitSystem {
    METRIC,
    IMPERIAL;

    companion object {
        fun fromName(name: String?) : UnitSystem {
            return runCatching { UnitSystem.valueOf(name?.uppercase() ?: "") }
                .getOrDefault(METRIC)
        }
    }
}