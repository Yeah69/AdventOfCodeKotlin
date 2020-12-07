import kotlin.streams.asSequence

class Day06 : Day() {
    override val label: String get() = "06"

    private val groups by lazy { input
        .split("\r\n\r\n")
        .asSequence()
        .map { it.lineSequence().map { line -> line.chars().asSequence().toList() }
        .toList() } }

    private fun logic(countVotes: (Sequence<Int>, Int) -> Int): String =
        groups.map { countVotes(it.asSequence().flatten(), it.count()) }.sum().toString()

    override fun taskZeroLogic(): String = logic { s, _ -> s.distinct().count() }

    override fun taskOneLogic(): String = logic { s, c -> s.groupBy { i -> i }.values.count { g -> g.count() == c } }
}