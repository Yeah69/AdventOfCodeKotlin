class Day10 : Day() {
    override val label: String get() = "10"

    private val outputJoltages by lazy { input.lineSequence().map(String::toInt).plus(0).sorted().toList() }

    override fun taskZeroLogic(): String {
        val device = outputJoltages.maxOf { it } + 3
        val groups = outputJoltages
            .asSequence()
            .plus(device)
            .sorted()
            .zipWithNext()
            .map { it.second - it.first }
            .groupBy { it }
        if (groups.containsKey(1).not() || groups.containsKey(3).not()) return noSolutionFound
        return (groups[1]!!.count() * groups[3]!!.count()).toString()
    }

    override fun taskOneLogic(): String {
        var trackedList = outputJoltages.map { Pair(it, 1L) }.toMutableList()
        for (i in (trackedList.count() - 2) downTo 0)
        {
            var newSecond = trackedList[i + 1].second
            if (i + 2 < trackedList.count() && trackedList[i + 2].first - trackedList[i].first <= 3)
                newSecond += trackedList[i + 2].second
            if (i + 3 < trackedList.count() && trackedList[i + 3].first - trackedList[i].first <= 3)
                newSecond += trackedList[i + 3].second
            trackedList[i] = Pair(trackedList[i].first, newSecond)
        }
        return trackedList[0].second.toString()
    }
}