package dev.mamkin.smartstep.core.domain.mappers

import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.domain.model.UnitSystem
import dev.mamkin.smartstep.core.domain.model.WeightUnit

fun UnitSystem.toWeightUnit(): WeightUnit = when (this) {
    UnitSystem.METRIC -> WeightUnit.KILOGRAM
    UnitSystem.IMPERIAL -> WeightUnit.POUND
}

fun UnitSystem.toHeightUnit(): HeightUnit = when (this) {
    UnitSystem.METRIC -> HeightUnit.CENTIMETER
    UnitSystem.IMPERIAL -> HeightUnit.FOOT_INCH
}
