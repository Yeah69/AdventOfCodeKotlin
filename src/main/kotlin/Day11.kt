import kotlin.streams.asSequence

class Day11 : Day() {
    override val label: String get() = "11"

    private val seatLayout by lazy { input
        .lineSequence()
        .flatMapIndexed { y, line ->
            line.chars()
                .asSequence()
                .mapIndexed { x, c -> Pair(Pair(x, y), c) }
                .filter { it.second == 'L'.toInt() }
                .map { Pair(it.first, false) } }
        .toMap() }

    private val maxX by lazy { seatLayout.keys.maxOf { it.first } }

    private val maxY by lazy { seatLayout.keys.maxOf { it.second } }

    private fun logic(
        tolerance: Int,
        mapDirection: (Pair<Int, Int>, Pair<Int, Int>, Collection<Pair<Int, Int>>) -> Pair<Int, Int>): String {
        var prevLayout = seatLayout.toMap()
        var currLayout = mutableMapOf<Pair<Int, Int>, Boolean>()
        do {
            var changeOccurred = false
            for (entry in prevLayout) {
                val occupation = listOf(
                    Pair(-1, -1), Pair( 0, -1), Pair( 1, -1),
                    Pair(-1,  0),               Pair( 1,  0),
                    Pair(-1,  1), Pair( 0,  1), Pair( 1,  1))
                    .asSequence()
                    .map { mapDirection(it, entry.key, prevLayout.keys) }
                    .mapNotNull { prevLayout[it] }
                    .count { it }
                val newSeatOccupation =
                    if (entry.value.not() && occupation == 0) true
                    else if (entry.value && occupation >= tolerance) false
                    else entry.value
                if (changeOccurred.not() && newSeatOccupation != entry.value)
                    changeOccurred = true
                currLayout[entry.key] = newSeatOccupation
            }
            prevLayout = currLayout
            currLayout = mutableMapOf()
        } while (changeOccurred)
        return prevLayout.values.count { it }.toString()
    }

    override fun taskZeroLogic(): String =
        logic(4) { dir, pos, _ -> Pair(pos.first + dir.first, pos.second + dir.second) }

    override fun taskOneLogic(): String =
        logic(5) { dir, pos, positions ->
            var curr = Pair(pos.first + dir.first, pos.second + dir.second)
            while (curr.first >= 0 && curr.second >= 0
                && curr.first <= maxX && curr.second <= maxY
                && positions.contains(curr).not())
                curr = Pair(curr.first + dir.first, curr.second + dir.second)
            curr }
}