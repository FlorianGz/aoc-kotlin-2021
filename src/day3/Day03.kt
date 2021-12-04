package day3

import readInput
import kotlin.math.pow

fun main() {
    val items = readInput("day3/Day03")

    val powerConsumption = computePowerConsumption(items)
    println("power Consumption = $powerConsumption")

    val oxygenGeneratorItem = getSubmarineSupportLifeItem(items = items, index = 0, mostFrequentFilter = true)
    val oxygenGeneratorRating = convertBinaryToDecimal(oxygenGeneratorItem.toLong())

    val co2ScrubberItem = getSubmarineSupportLifeItem(items = items, index = 0, mostFrequentFilter = false)
    val co2ScrubberRating = convertBinaryToDecimal(co2ScrubberItem.toLong())

    val lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating
    println("life support rating = $lifeSupportRating")
}

private fun computePowerConsumption(items: List<String>): Int {
    var gammaRate = ""
    var epsilonRate = ""

    for (i in items.first().indices) {
        gammaRate += getSelectedBit(items.map { it[i].toString() }, true)
    }

    gammaRate.forEach {
        epsilonRate += if (it.toString() == "0") "1" else "0"
    }

    val gammaDecimalValue = convertBinaryToDecimal(gammaRate.toLong())
    val epsilonDecimalValue = convertBinaryToDecimal(epsilonRate.toLong())
    return gammaDecimalValue * epsilonDecimalValue
}

private fun getSubmarineSupportLifeItem(items: List<String>, index: Int = 0, mostFrequentFilter: Boolean = true): String {
    val selectedBit = getSelectedBit(items.map { it[index].toString() }, mostFrequentFilter)

    var filteredItems = items.mapNotNull {
        if (it[index].toString() == selectedBit) {
            it
        } else {
            null
        }
    }

    if (index < items.first().length - 1 && filteredItems.size > 1) {
        filteredItems = listOf(getSubmarineSupportLifeItem(filteredItems, index + 1, mostFrequentFilter))
    }

    return filteredItems.first()
}

private fun getSelectedBit(items: List<String>, mostFrequent: Boolean): String {
    var countZero = 0
    var countOne = 0

    items.forEach {
        if (it == "0") {
            countZero++
        } else {
            countOne++
        }
    }
    return if (mostFrequent) {
        if (countZero > countOne) "0" else "1"
    } else {
        if (countZero <= countOne) "0" else "1"
    }
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

