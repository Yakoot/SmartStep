package dev.mamkin.smartstep.core.domain.model

enum class HeightUnit {
    CENTIMETER,
    FOOT_INCH;

    companion object {
        fun fromName(name: String?) : HeightUnit? {
            return runCatching { HeightUnit.valueOf(name?.uppercase() ?: "") }
                .getOrNull()
        }
    }
}