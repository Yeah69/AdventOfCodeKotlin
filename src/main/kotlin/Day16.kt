class Day16 : Day() {
    override val label: String get() = "16"

    private val ruleRegex: Regex = """^(.+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)$""".toRegex()

    data class Rule(val fieldName: String, val firstRange: IntRange, val secondRange: IntRange){
        fun valid(fieldValue: Int): Boolean =
            this.firstRange.contains(fieldValue)
                    || this.secondRange.contains(fieldValue)
    }

    private val rules by lazy { input
        .lineSequence()
        .mapNotNull { ruleRegex.find(it) }
        .map {
            val (fieldName, from0, to0, from1, to1) = it.destructured
            Rule(fieldName, from0.toInt()..to0.toInt(), from1.toInt()..to1.toInt())
        } }

    private val myTicket by lazy { input
        .lineSequence()
        .dropWhile { it != "your ticket:" }
        .drop(1)
        .take(1)
        .flatMap { line -> line.split(",").mapNotNull { it.toIntOrNull() } }
        .toList() }

    private val nearbyTickets by lazy { input
        .lineSequence()
        .dropWhile { it != "nearby tickets:" }
        .drop(1)
        .map { line -> line.split(",").mapNotNull { it.toIntOrNull() }.toList() }
        .toList() }

    private val validTickets by lazy { nearbyTickets
        .asSequence()
        .filter { it.all { fieldValue -> rules.any { rule -> rule.valid(fieldValue) } } }
        .toList() }

    override fun taskZeroLogic(): String =
        nearbyTickets
            .flatten()
            .filter { rules.none { rule -> rule.valid(it) || rule.secondRange.contains(it) } }
            .sum()
            .toString()

    override fun taskOneLogic(): String {
        val map = rules
            .map { Pair(
                it,
                (0 until validTickets.first().count())
                    .filter { i -> validTickets.all { ticket -> it.valid(ticket[i]) } }.toList()) }
            .toMap()

        val excludes = mutableSetOf<Int>()

        val ruleToIndex = map
            .asSequence()
            .sortedBy { it.value.count() }
            .map {
                val i = it.value.asSequence().filter { index -> excludes.contains(index).not() }.first()
                excludes.add(i)
                Pair(it.key, i)
            }
            .toMap()

        return ruleToIndex
            .asSequence()
            .filter { it.key.fieldName.startsWith("departure") }
            .map { myTicket[it.value] }
            .fold(1L) { prev, curr -> prev * curr.toLong() }
            .toString()
    }
}