class Day19 : Day() {
    override val label: String get() = "19"

    private interface Rule {
        fun buildRegexString(): String
    }

    private class EmptyRule : Rule {
        override fun buildRegexString() = ""
    }

    private class CharRule(val char: Char) : Rule {
        override fun buildRegexString() = char.toString()
    }

    private class SpecialRuleZero(val fortyTwo: Rule, val thirtyOne: Rule) : Rule {
        override fun buildRegexString() = "(${fortyTwo.buildRegexString()})+(${thirtyOne.buildRegexString()})+"
    }

    private class CompositeRule(val ruleLists: List<List<Rule>>) : Rule {
        override fun buildRegexString(): String {
            val text = ruleLists
                .map { it.map { rule -> rule.buildRegexString() }.joinToString("") }
                .joinToString("|")
            return if(ruleLists.count() > 1) "($text)" else text }
    }

    private class RuleHolder : Rule {
        var innerRule: Rule = EmptyRule()

        override fun buildRegexString() = innerRule.buildRegexString()
    }

    private val rules by lazy {
        val textMap = input
            .lineSequence()
            .takeWhile { it.contains(':') }
            .map {
                val split = it.split(':')
                split[0].trim().toInt() to split[1].trim()
            }
            .toMap()
        val holderMap = textMap
            .map { it.key to RuleHolder() }
            .toMap()
        for(entry in textMap) {
            val ruleLists = entry
                .value
                .splitToSequence('|')
                .map { list -> list
                    .trim()
                    .splitToSequence(' ')
                    .map { it.replace("\"", "") }
                    .map {
                        if (it.length == 1 && it[0].isLetter()) CharRule(it[0])
                        else  holderMap[it.toInt()] ?: throw Exception() }
                    .toList() }
                .toList()

            holderMap[entry.key]?.innerRule = CompositeRule(ruleLists)
        }
        holderMap
    }

    private val ruleZero by lazy { (rules[0]?.innerRule?.buildRegexString() ?: throw Exception()).toRegex() }

    private val ruleOne by lazy {
        Triple(
            SpecialRuleZero(rules[42]!!, rules[31]!!).buildRegexString().toRegex(),
            rules[42]?.innerRule?.buildRegexString()?.toRegex() ?: throw Exception(),
            rules[31]?.innerRule?.buildRegexString()?.toRegex() ?: throw Exception()) }

    private val messages by lazy { input
        .lineSequence()
        .dropWhile { it.contains(':') || it.isBlank() }
        .toList() }

    fun logic(regex: Regex, predicate: (MatchResult) -> Boolean): String = messages
        .asSequence()
        .mapNotNull(regex::matchEntire)
        .filter(predicate)
        .count()
        .toString()

    override fun taskZeroLogic(): String = logic(ruleZero) { true }

    override fun taskOneLogic(): String = logic(ruleOne.first) {
        fun String.getCountAndRest(regex: Regex): Pair<Int, String> {
            var text = this
            var count = 0
            while (true) {
                val find = regex.find(text)
                if (find == null || find.range.first != 0) break
                text = text.removeRange(find.range)
                count++
            }
            return count to text
        }
        val (fortyTwo, rest) = it.value.getCountAndRest(ruleOne.second)
        val (thirtyOne, _) = rest.getCountAndRest(ruleOne.third)
        fortyTwo > thirtyOne
    }
}