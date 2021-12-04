package day3

import readInput
import kotlin.math.pow

fun main() {
    val items = readInput("day3/Day03")
    //println("power Consumption = ${computePowerConsumption()}")
    val oxygenGeneratorItem = computeOxygenGenerator(items)
    val oxygenGeneratorRating = convertBinaryToDecimal(oxygenGeneratorItem.toLong())

    println("last item oxygen = $oxygenGeneratorItem")
    println("oxygenValue = $oxygenGeneratorRating")

    val co2ScrubberItem = computeC02Scrubber(items)
    val co2ScrubberRating = convertBinaryToDecimal(co2ScrubberItem.toLong())

    println("last item co2 = $co2ScrubberItem")
    println("co2ScrubberRating = $co2ScrubberRating")

    val lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating
    println("life support rating = $lifeSupportRating")
}

private fun computePowerConsumption(items: List<String>): Int {
    var gammaRate = ""
    var epsilonRate = ""

    for (i in items.first().indices) {
        gammaRate += getMostCommonBit(items.map { it[i].toString() })
    }

    gammaRate.forEach {
        epsilonRate += if (it.toString() == "0") "1" else "0"
    }

    val gammaDecimalValue = convertBinaryToDecimal(gammaRate.toLong())
    val epsilonDecimalValue = convertBinaryToDecimal(epsilonRate.toLong())
    return gammaDecimalValue * epsilonDecimalValue
}

private fun computeOxygenGenerator(items: List<String>, index: Int = 0): String {
    val mostCommonBit = getMostCommonBit(items.map { it[index].toString() })
    println("most common bit = $mostCommonBit")
    var filteredItems = items.mapNotNull {
        if (it[index].toString() == mostCommonBit) {
            it
        } else {
            null
        }
    }
    println("filtered item $filteredItems")

    if (index < items.first().length - 1 && filteredItems.size > 1) {
        filteredItems = listOf(computeOxygenGenerator(filteredItems, index + 1))
    }

    return filteredItems.first()
}

private fun computeC02Scrubber(items: List<String>, index: Int = 0): String {
    val leastCommonBit = getLeastCommonBit(items.map { it[index].toString() })
    var filteredItems = items.mapNotNull {
        if (it[index].toString() == leastCommonBit) {
            it
        } else {
            null
        }
    }

    if (index < items.first().length - 1 && filteredItems.size > 1) {
        filteredItems = listOf(computeC02Scrubber(filteredItems, index + 1))
    }

    return filteredItems.first()
}

private fun getMostCommonBit(items: List<String>): String {
    var countZero = 0
    var countOne = 0

    items.forEach {
        if (it == "0") {
            countZero++
        } else {
            countOne++
        }
    }

    return if (countZero > countOne) "0" else "1"
}

private fun getLeastCommonBit(items: List<String>): String {
    var countZero = 0
    var countOne = 0

    items.forEach {
        if (it == "0") {
            countZero++
        } else {
            countOne++
        }
    }

    return if (countZero <= countOne) "0" else "1"
}

private fun convertBinaryToDecimal(num: Long): Int {
    var num = num
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * 2.0.pow(i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

