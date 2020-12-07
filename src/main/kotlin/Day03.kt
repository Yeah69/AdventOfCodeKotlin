class Day03 : Day() {
    override val label: String get() = "03"

    private val map by lazy { input
        .lines()
        .asSequence()
        .flatMapIndexed { y, line -> line
            .toCharArray()
            .mapIndexed { x, c -> Pair(x, c)}
            .filter { it.second == '#'}
            .map { Pair(it.first.toLong(), y.toLong()) } }
        .toHashSet() }

    private val xRepeatBorder by lazy { input
        .lines()
        .asSequence()
        .map { it.length }
        .maxOf { it } }

    private val yBorder by lazy { input
        .lines()
        .count() }

    private fun logic(increment: Pair<Long, Long>): Long =
        sequence {
            var position = Pair(0L, 0L)
            while (true) {
                yield(position)
                position = Pair(
                    position.first + increment.first,
                    position.second + increment.second)
            } }
            .takeWhile { it.second < yBorder }
            .map { Pair(it.first % xRepeatBorder, it.second) }
            .count(map::contains)
            .toLong()

    override fun taskZeroLogic(): String = logic(Pair(3, 1)).toString()

    override fun taskOneLogic(): String = (
            logic(Pair(1L, 1L))
            * logic(Pair(3L, 1L))
            * logic(Pair(5L, 1L))
            * logic(Pair(7L, 1L))
            * logic(Pair(1L, 2L)))
        .toString()
}