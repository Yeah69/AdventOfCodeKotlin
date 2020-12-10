class Day09 : Day() {
    override val label: String get() = "09"

    private val numbers by lazy { input.lineSequence().map(String::toLongOrNull).filterNotNull().toList() }

    private val checkSequence = sequence {
        val preamble = numbers.take(25).toMutableList()
        for (i in 26 until numbers.count())
        {
            yield(Pair(preamble, numbers[i]))
            preamble.removeAt(0)
            preamble.add(numbers[i])
        } }

    private val zeroSolution by lazy { checkSequence
        .filter { it.first.asSequence().allPairs().none { p -> p.first + p.second == it.second } }
        .map { it.second }
        .firstOrNull() }

    override fun taskZeroLogic(): String = zeroSolution?.toString() ?: noSolutionFound

    override fun taskOneLogic(): String {
        if (zeroSolution == null) return noSolutionFound
        val seekedSum = zeroSolution as Long
        var firstIndex = 0
        var sum = 0L
        for (i in 0 until numbers.count()) {
            if (sum < seekedSum) sum += numbers[i]

            if (sum == zeroSolution) {
                val range = numbers.subList(firstIndex, i)
                return (range.maxOf { it } + range.minOf { it }).toString()
            }

            while (sum > seekedSum) {
                sum -= numbers[firstIndex]
                firstIndex++
            }
        }
        return noSolutionFound
    }
}