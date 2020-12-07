class Day07 : Day() {
    override val label: String
        get() = "07"

    private val lineRegex: Regex = """^(.+) bags contain (.+).$""".toRegex()

    private val listItemRegex: Regex = """^([0-9]+) (.+) bags?$""".toRegex()

    private val bagRules by lazy { input
        .lineSequence()
        .map {
            val (label, list) = lineRegex.find(it)!!.destructured
            val map =
                if (list != "no other bags") list
                    .split(", ")
                    .map { item ->
                        val (amount, itemLabel) = listItemRegex.find(item)!!.destructured
                        Pair(itemLabel, SubBaggage(itemLabel, amount.toInt())) }
                    .toMap()
                else mapOf()
            Pair(label, Bag(label, map)) }
        .toMap() }

    private fun containsSubBag(bag: Bag, seekedLabel: String, rules: Map<String, Bag>): Boolean
    {
        if (bag.subBaggage.containsKey(seekedLabel)) return true
        for (subBaggage in bag.subBaggage.keys)
            if(containsSubBag(rules[subBaggage]!!, seekedLabel, rules))
                return true
        return false
    }

    private fun countBaggage(bag: Bag, rules: Map<String, Bag>): Int
    {
        var count = 0
        for (subBaggage in bag.subBaggage.values)
        {
            val subBag = rules[subBaggage.label]!!
            count += countBaggage(subBag, rules) * subBaggage.amount + subBaggage.amount
        }
        return count
    }

    override fun taskZeroLogic(): String =
        bagRules
            .values
            .asSequence()
            .filter { containsSubBag(it, "shiny gold", bagRules) }
            .count()
            .toString()

    override fun taskOneLogic(): String = countBaggage(bagRules["shiny gold"]!!, bagRules).toString()

    data class SubBaggage(val label: String, val amount: Int)

    data class Bag(val label: String, val subBaggage: Map<String, SubBaggage>)
}