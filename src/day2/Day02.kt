package day2

import readInput

fun main() {
    val items = readInput("day2/Day02")
    println("total = ${computeSubmarinePosition(items)}")
}

private fun computeSubmarinePosition(items: List<String>): Int {
    var horizontalPosition = 0
    var aim = 0
    var depth = 0

    items.forEach {
        val submarineMove = Regex("(\\w+) (\\d+)").find(it)
        submarineMove?.let {
            val (move, value) = submarineMove.destructured
            when (move) {
                "forward" -> {
                    horizontalPosition += value.toInt()
                    depth += (aim * value.toInt())
                }
                "up" -> aim -= value.toInt()
                "down" -> aim += value.toInt()
            }
        }
    }
    return depth * horizontalPosition
}
