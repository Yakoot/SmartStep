package dev.mamkin.smartstep.core.domain.model

enum class Gender {
    Female,
    Male;

    companion object {
        fun fromName(name: String?) : Gender? {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) }
        }
    }
}