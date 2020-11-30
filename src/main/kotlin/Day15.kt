import java.lang.Exception

class Day15 : Day() {
    override val label: String get() = "15"

    private val startingNumbers by lazy { input
        .splitToSequence(',')
        .mapNotNull(String::toIntOrNull)
        .toList() }

    private fun logic(roundCount: Int): String {
        fun MutableMap<Int, Int>.addAndNext(pair: Pair<Int, Int>): Pair<Int, Int> {
            val (i, last) = pair
            val next = if (this.containsKey(last)) { i - (this[last] ?: throw Exception()) } else { 0 }
            this[last] = i
            return Pair(i + 1, next)
        }

        val map = mutableMapOf<Int, Int>()

        startingNumbers
            .take(startingNumbers.count() - 1)
            .forEachIndexed { i, value -> map.addAndNext(Pair(i + 1, value)) }

        return generateSequence(Pair(startingNumbers.count(), startingNumbers.last()), map::addAndNext)
            .takeWhile { it.first <= roundCount }
            .last()
            .second
            .toString()
    }

    override fun taskZeroLogic(): String = logic(2020)

    override fun taskOneLogic(): String  = logic(30000000)
}