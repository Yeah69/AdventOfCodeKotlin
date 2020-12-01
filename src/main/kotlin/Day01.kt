class Day01 : Day {
    private val seekedSum = 2020

    override fun taskZeroLogic(input: String): String {
        val expenses = getExpenses(input)

        for (i in expenses.indices) {
            for (j in expenses.indices) {
                if (i != j
                    && expenses[i] + expenses[j] == seekedSum) {
                    return (expenses[i] * expenses[j]).toString();
                }
            }
        }
        return "No solution found"
    }
    override fun taskOneLogic(input: String): String {
        val expenses = getExpenses(input)

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
    private fun getExpenses(input: String): List<Int> = input.split('\n').map { it.toInt() }.toList()
}