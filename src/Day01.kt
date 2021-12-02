fun main() {
    val items = readInput("Day01").map { it.toInt() }
    val triples = items.windowed(3, 1).map { Triple(it[0], it[1], it[2]) }

    println(countIncreasedItem(items))
    println(countIncreasedNumberOfTriples(triples))
}

private fun countIncreasedNumberOfTriples(items: List<Triple<Int, Int, Int>>): Int {
    var count = 0
    items.forEachIndexed { index, item ->
        if (index > 0 && index <= items.size - 1) {
            val previousSum = items[index - 1].toList().sum()
            val currentSum = item.toList().sum()
            if (currentSum > previousSum) count++
        }
    }
    return count
}


private fun countIncreasedItem(items: List<Int>): Int {
    var count = 0
    items.forEachIndexed { index, item ->
        if (index > 0 && index <= items.size - 1) {
            val previousItem = items[index - 1]
            if (item > previousItem) count++
        }
    }
    return count
}
