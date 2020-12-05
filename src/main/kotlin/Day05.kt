import kotlin.math.pow

class Day05 : Day() {
    override val label: String
        get() = "05"

    private fun String.getCoordinate(distantDirection: Char): Int =
        this.asSequence()
            .mapIndexed { i, c -> Pair(i, c) }
            .filter { it.second == distantDirection }
            .map { 2.toDouble().pow((this.length - 1 - it.first).toDouble()).toInt() }
            .sum()

    private val seatingCoordinates by lazy {
        input.lineSequence()
            .map { Pair(it.substring(0..6), it.substring(7..9)) }
            .map { Pair(it.first.getCoordinate('B'), it.second.getCoordinate('R')) }
            .toList() }

    private val seatingIds by lazy { seatingCoordinates.map { it.first * 8 + it.second }.toList() }

    override fun taskZeroLogic(): String = seatingIds.maxOf { it }.toString()

    override fun taskOneLogic(): String =
        seatingIds
            .asSequence()
            .sorted()
            .zipWithNext()
            .filter { it.first + 2 == it.second }
            .map { it.first + 1 }
            .singleOrNull()
            ?.toString()
            ?: "no solution found"
}