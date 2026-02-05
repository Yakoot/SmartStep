package dev.mamkin.smartstep.core.domain.model

enum class Gender {
    Female,
    Male;

    companion object {
        fun fromName(name: String?) : Gender? {
            return runCatching { Gender.valueOf(name?.uppercase() ?: "") }
                .getOrNull()
        }
    }
}