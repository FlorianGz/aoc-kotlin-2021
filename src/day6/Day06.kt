package day6

import readInput

fun main() {
    val items =  readInput("day6/Day06")
    val timers = items.first().split(",").map { it.toLong() }

    println("count = ${getNumberOfFish(256, timers)}")
}

private fun getNumberOfFish(days: Long, items: List<Long>) : Long {
    var fishLifecycleMap = items.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
    var fishLifecycleMapCopy = mutableMapOf<Long, Long>()
    fishLifecycleMapCopy.putAll(fishLifecycleMap)

    (1..days).forEach { _ ->
        var fishBorn = 0L
        fishLifecycleMapCopy.toSortedMap(compareBy { it }).forEach { (key, value) ->
            if (key != 0L) {
                fishLifecycleMap[key] = 0
                fishLifecycleMap[key - 1] = fishLifecycleMap.getOrDefault(key - 1, 0) + value
            } else {
                fishLifecycleMap[0] = fishLifecycleMap.getOrDefault(0, 1) - value
                fishBorn = value
            }
        }
        if (fishBorn > 0) {
            fishLifecycleMap[6] =  fishLifecycleMap.getOrDefault(6, 0) + fishBorn
            fishLifecycleMap[8] =  fishLifecycleMap.getOrDefault(8, 0) + fishBorn
        }

        fishLifecycleMapCopy.clear()
        fishLifecycleMapCopy.putAll(fishLifecycleMap)
    }

    return fishLifecycleMap.map { it.value }.sum()
}
