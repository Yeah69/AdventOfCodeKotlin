class Day04 : Day() {
    override val label: String get() = "04"

    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    private val validYearFormRegex: Regex = """^([0-9]{4})$""".toRegex()
    private val validHeightFormRegex: Regex = """^([0-9]{3}cm|[0-9]{2}in)$""".toRegex()
    private val validHairColorFormRegex: Regex = """^#[0-9a-f]{6}$""".toRegex()
    private val validEyeColorFormRegex: Regex = """^(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex()
    private val validPassportIdFormRegex: Regex = """^([0-9]{9})$""".toRegex()

    private val passports by lazy { input
        .split("\r\n\r\n")
        .asSequence()
        .map { it
            .lineSequence()
            .flatMap { line -> line.split(' ') }
            .map { entryText ->
                val entry = entryText.split(':')
                Pair(entry[0], entry[1])
            }.toList() }
        .toList() }

    private fun logic(isValid: (List<Pair<String, String>>) -> Boolean): String =
        passports
            .filter { it.count() >= 7 }
            .filter { fields -> requiredFields.all { fields.any { f -> f.first == it } } }
            .filter(isValid)
            .count()
            .toString()

    override fun taskZeroLogic(): String = logic { true }

    override fun taskOneLogic(): String = logic {

        it.all { field ->
            val data = field.second
            when (field.first) {
                "byr" -> validYearFormRegex.matches(data) && data >= "1920" && data <= "2002"
                "iyr" -> validYearFormRegex.matches(data) && data >= "2010" && data <= "2020"
                "eyr" -> validYearFormRegex.matches(data) && data >= "2020" && data <= "2030"
                "hgt" -> validHeightFormRegex.matches(data)
                        && (data.endsWith("cm") && data >= "150cm" && data <= "193cm"
                        || data.endsWith("in") && data >= "59in" && data <= "76in")
                "hcl" -> validHairColorFormRegex.matches(data)
                "ecl" -> validEyeColorFormRegex.matches(data)
                "pid" -> validPassportIdFormRegex.matches(data)
            else -> true
        } }
    }
}