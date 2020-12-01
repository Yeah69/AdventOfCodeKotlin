class Day01 : Day {
    private val seekedSum = 2020

    override fun taskZeroLogic(input: String): String {
        val maybePair = input.parseExpenses().allPairs().firstOrNull { pair -> pair.first + pair.second == seekedSum }
        return if (maybePair is Pair<Int,Int>)
                (maybePair.first * maybePair.second).toString()
        else "no solution found"
    }
    override fun taskOneLogic(input: String): String {
        val expenses = input.parseExpenses()

        for (i in expenses.indices) {
            for (j in expenses.indices) {
                for (k in expenses.indices) {
                    if (i != j && i !=k && j != k
                        && expenses[i] + expenses[j] + expenses[k] == seekedSum) {
                        return (expenses[i] * expenses[j] * expenses[k]).toString();
                    }
                }
            }
        }
        return "No solution found"
    }
    private fun String.parseExpenses(): List<Int> = this.split('\n').map { it.toInt() }.toList()
    private fun <T> List<T>.allPairs(): Sequence<Pair<T, T>> {
        val list = this
        return sequence {
            val iterated = mutableListOf<T>()
            for (i: T in list) {
                for (j: T in iterated) {
                    yield(Pair(i, j))
                }
                iterated.add(i)
            }
        }
    }
}