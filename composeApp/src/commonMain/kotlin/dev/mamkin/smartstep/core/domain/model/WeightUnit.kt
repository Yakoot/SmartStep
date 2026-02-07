package dev.mamkin.smartstep.core.domain.model

enum class WeightUnit {
    KILOGRAM,
    POUND;

    companion object {
        fun fromName(name: String?) : WeightUnit? {
            return runCatching { WeightUnit.valueOf(name?.uppercase() ?: "") }
                .getOrNull()
        }
    }
}