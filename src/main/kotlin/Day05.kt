class Day05 : Day() {
    override val label: String get() = "05"

    private val seatingIds by lazy {
        input.lineSequence()
            .map { it
                .reversed()
                .asSequence()
                .map { c -> if (c == 'R' || c == 'B') 1 else 0 }
                .mapIndexed { i, j -> j shl i }
                .sum() }
            .toList() }

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
            ?: noSolutionFound
}