package day6

import readInput
import kotlin.math.exp
import kotlin.math.pow

fun main() {
    val items =  readInput("day6/Day06_test")
    val timers = items.first().split(",").map { it.toInt() }

    println("count = ${getNumberOfFish(80, timers)}")
}

private fun getNumberOfFish(days: Int, items: List<Int>) : Int {
    val intervalTimers = mutableListOf<Int>()
    intervalTimers.addAll(items)

    (1..days).forEach { day ->
        val iterator = intervalTimers.listIterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem != 0) {
                iterator.set(currentItem - 1)
            } else {
                iterator.set(6)
                iterator.add(8)
            }
        }
    }

    return intervalTimers.size
}
