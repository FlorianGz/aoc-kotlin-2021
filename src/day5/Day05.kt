import day5.HydrothermalLine

fun main() {
    val regex = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")
    val items =  readInput("day5/Day05")
    val hydrothermalLines = items.mapNotNull {
        regex.matchEntire(it)?.destructured?.let { (x1, y1, x2, y2) ->
            HydrothermalLine(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }
    }

    countHydrothermalLines(hydrothermalLines, false)
    countHydrothermalLines(hydrothermalLines, true)
}

fun countHydrothermalLines(items: List<HydrothermalLine>, withDiagonals: Boolean) {
    val linesCoordinates = mutableMapOf<Pair<Int, Int>, Int>()

    items.forEach { line ->
        val x1 = line.x1
        val y1 = line.y1
        val x2 = line.x2
        val y2 = line.y2

        when {
            y1 == y2 -> {
                val (min, max) = if (x1 > x2) x2 to x1 else x1 to x2
                (min..max).forEach { y ->
                    linesCoordinates[y to y1] = linesCoordinates.getOrDefault(y to y1, 0) + 1
                }
            }
            x1 == x2 -> {
                val (min, max) = if (y1 > y2) y2 to y1 else y1 to y2
                (min..max).forEach { x ->
                    linesCoordinates[x1 to x] = linesCoordinates.getOrDefault(x1 to x, 0) + 1
                }
            } else -> {
                if (withDiagonals) {
                    if (x1 > x2) {
                        (x2..x1).forEachIndexed { index, _ ->
                            val y = if (y1 > y2) y2 + index else y2 - index
                            linesCoordinates[x2 + index to y] = linesCoordinates.getOrDefault(x2 + index to y, 0) + 1
                        }
                    } else {
                        (x1..x2).forEachIndexed { index, _ ->
                            val y = if (y1 > y2) y1 - index else y1 + index
                            linesCoordinates[x1 + index to y] = linesCoordinates.getOrDefault(x1 + index to y, 0) + 1
                        }
                    }
                }
            }
        }
    }

    println(linesCoordinates.count { it.value > 1 })
}