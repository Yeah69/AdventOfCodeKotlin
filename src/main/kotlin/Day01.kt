class Day01 : Day() {
    private val seekedSum = 2020
    override val label: String get() = "01"

    private val expenses by lazy { input.lines().map { it.toInt() }.toList() }

    override fun taskZeroLogic(): String {
        fun isSeekedPair(pair: Pair<Int, Int>):Boolean = pair.first + pair.second == seekedSum
        fun calculateSolution(pair: Pair<Int, Int>):String = (pair.first * pair.second).toString()

        return expenses
            .asSequence()
            .allPairs()
            .filter(::isSeekedPair)
            .map(::calculateSolution)
            .firstOrNull()
            ?: noSolutionFound
    }
    override fun taskOneLogic(): String {
        fun isSeekedTriple(triple: Triple<Int, Int, Int>):Boolean = triple.first + triple.second + triple.third == seekedSum
        fun calculateSolution(triple: Triple<Int, Int, Int>):String = (triple.first * triple.second * triple.third).toString()

        return expenses
            .asSequence()
            .allTriples()
            .filter(::isSeekedTriple)
            .map(::calculateSolution)
            .firstOrNull()
            ?: noSolutionFound
    }
}