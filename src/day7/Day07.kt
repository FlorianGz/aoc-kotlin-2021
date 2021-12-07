package day7

import readInput
import kotlin.math.abs

fun main() {
    val items =  readInput("day7/Day07")
    val crabsPositions = items.first().split(",").map { it.toInt() }

    getBestCrabsPosition(crabsPositions)
}

fun getBestCrabsPosition(crabsPositions: List<Int>) {
    val fuelConsumption = mutableMapOf<Int, Int>()
    val min = crabsPositions.minByOrNull { it }!!
    val max = crabsPositions.maxByOrNull { it }!!

    (min..max).forEach { magicPos ->
        var fuel = 0
        crabsPositions.forEach { crabPos ->
            val diff = abs(magicPos - crabPos)
            val additionalCost = (1..diff).sum()

            fuel += additionalCost
        }
        fuelConsumption[magicPos] = fuel
    }

    println("Cheapest Position : ${fuelConsumption.minByOrNull { it.value }!!}")
}
