package dev.mamkin.smartstep.core.domain.model

import kotlin.math.roundToInt

object UnitConverter {

    fun cmToFtIn(cm: Int): Pair<Int, Int> {
        val totalInches = cm / 2.54
        val ft = (totalInches / 12).toInt()
        val inch = (totalInches % 12).roundToInt()
        return if (inch == 12) (ft + 1) to 0 else ft to inch
    }

    fun ftInToCm(ft: Int, inch: Int): Int {
        return ((ft * 12 + inch) * 2.54).roundToInt()
    }

    fun kgToLb(kg: Int): Int {
        return (kg * 2.20462).roundToInt()
    }

    fun lbToKg(lb: Int): Int {
        return (lb / 2.20462).roundToInt()
    }
}