import java.lang.Exception

class Day07 : Day() {
    override val label: String get() = "07"

    private val seekedLabel = "shiny gold"

    private val lineRegex: Regex = """^(.+) bags contain (.+).$""".toRegex()

    private val listItemRegex: Regex = """^([0-9]+) (.+) bags?$""".toRegex()

    private val bagRules by lazy { input
        .lineSequence()
        .map {
            val (label, list) = lineRegex.find(it)?.destructured
                ?: throw Exception("Un-destructure-able")
            val map =
                if (list != "no other bags") list
                    .split(", ")
                    .map { item ->
                        val (amount, itemLabel) = listItemRegex.find(item)?.destructured
                            ?: throw Exception("Un-destructure-able")
                        Pair(itemLabel, amount.toInt()) }
                    .toMap()
                else mapOf()
            Pair(label,  map) }
        .toMap() }

    private fun containsSeekedBag(bag: String): Boolean
    {
        val subBaggage = bagRules[bag] ?: throw Exception("Not found")
        if (subBaggage.containsKey(seekedLabel)) return true
        for (subBag in subBaggage.keys) if (containsSeekedBag(subBag)) return true
        return false
    }

    private fun countBaggage(bag: String): Int
    {
        val subBaggage = bagRules[bag] ?: throw Exception("Not found")
        var count = 0
        for ((label, amount) in subBaggage) count += countBaggage(label) * amount + amount
        return count
    }

    override fun taskZeroLogic(): String =
        bagRules.keys.asSequence().filter { containsSeekedBag(it) }.count().toString()

    override fun taskOneLogic(): String = countBaggage(seekedLabel).toString()
}