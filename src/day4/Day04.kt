package day4

import readInput
import java.util.*

fun main() {
    val items = readInput("day4/Day04")
    val numbers = items.first().split(",")
    val boards = getAllBoards(items.slice(2 until items.size))

    val firstWinnerBoard = getFirstWinnerBoard(numbers, boards)
    val lastWinnerBoard = getLastWinnerBoard(numbers, boards)

    val scoreForFirstWinnerBoard = getFinalScore(boards[firstWinnerBoard.index], firstWinnerBoard)
    val scoreForLastWinnerBoard = getFinalScore(boards[lastWinnerBoard.index], lastWinnerBoard)

    println("Final score for first winner : $scoreForFirstWinnerBoard")
    println("Final score for last winner : $scoreForLastWinnerBoard")
}

private fun getFirstWinnerBoard(numbers: List<String>, boards: List<List<List<String>>>): WinnerBoard {
    val markedItems = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    numbers.forEach { number ->
        boards.forEachIndexed { boardIndex, board ->
            board.forEachIndexed { index, lines ->
                if (lines.contains(number)) {
                    val markedIndex = lines.indexOf(number)
                    markedItems.add(Pair(boardIndex, Pair(index, markedIndex)))
                }
            }
        }
        val winnerBoardIndex = checkFirstWinnerBoard(markedItems)
        if (winnerBoardIndex != -1) {
            return WinnerBoard(
                index = winnerBoardIndex,
                magicNumber = number.toInt(),
                markedItems = markedItems.filter { it.first == winnerBoardIndex }.map { it.second }
            )
        }
    }

    return WinnerBoard(-1, -1, emptyList())
}

private fun getLastWinnerBoard(numbers: List<String>, boards: List<List<List<String>>>): WinnerBoard {
    var winners = mutableListOf<WinnerBoard>()
    val markedItems = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    numbers.forEach { number ->
        boards.forEachIndexed { boardIndex, board ->
            board.forEachIndexed { index, lines ->
                if (lines.contains(number)) {
                    val markedIndex = lines.indexOf(number)
                    markedItems.add(Pair(boardIndex, Pair(index, markedIndex)))
                }
            }
        }

        val winnersIndex = checkWinnerBoards(markedItems, winners.map { it.index })
        winnersIndex.forEach { winIndex ->
            val winner = WinnerBoard(
                index = winIndex,
                magicNumber = number.toInt(),
                markedItems = markedItems.filter { it.first == winIndex }.map { it.second }
            )
            winners.add(winner)
        }
    }

    return winners.last()
}

private fun getAllBoards(items: List<String>): List<List<List<String>>> {
    val boardLines = mutableListOf<List<String>>()
    val delimiter = "\\s+".toRegex()

    items.forEach {
        if (it.isNotEmpty()) {
            boardLines.add(it.trim().split(delimiter))
        }
    }

    return boardLines.chunked(5)
}

private fun checkFirstWinnerBoard(boards: List<Pair<Int, Pair<Int, Int>>>): Int {
    for (i in boards.indices) {
        val markedEntries = boards.filter { it.first == i }.map { it.second }
        val raw = markedEntries.map { it.first }
        val columns = markedEntries.map { it.second }
        for (a in 0..4) {
            val occurrencesRaw = Collections.frequency(raw, a)
            val occurrencesColum = Collections.frequency(columns, a)
            if (occurrencesRaw == 5 || occurrencesColum == 5) {
                return i
            }
        }
    }

    return -1
}

private fun checkWinnerBoards(markedList: List<Pair<Int, Pair<Int, Int>>>, winnerList: List<Int>): List<Int> {
    var winners = mutableListOf<Int>()
    for (i in markedList.indices) {
        if (!winnerList.contains(i)) {
            val markedEntries = markedList.filter { it.first == i }.map { it.second }
            val raw = markedEntries.map { it.first }
            val columns = markedEntries.map { it.second }
            for (a in 0..4) {
                val occurrencesRaw = Collections.frequency(raw, a)
                val occurrencesColum = Collections.frequency(columns, a)
                if (occurrencesRaw == 5 || occurrencesColum == 5) {
                    winners.add(i)
                }
            }
        }
    }

    return winners
}

private fun getFinalScore(board: List<List<String>>, winnerBoard: WinnerBoard): Int {
    var sum = 0
    board.forEachIndexed { rawIndex, lines ->
        lines.forEachIndexed { columnIndex, entry ->
            if (!winnerBoard.markedItems.contains(Pair(rawIndex, columnIndex))) {
                sum += entry.toInt()
            }
        }
    }

    return sum * winnerBoard.magicNumber
}
