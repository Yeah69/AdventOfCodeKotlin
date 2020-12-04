class Day04 : Day() {
    override val label: String
        get() = "04"

    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    private val validYearFormRegex: Regex = """^([0-9]{4})$""".toRegex()
    private val validHeightFormRegex: Regex = """^([0-9]+)(cm|in)$""".toRegex()
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
        it.all { field -> when (field.first) {
            "byr" -> validYearFormRegex.matches(field.second) && field.second >= "1920" && field.second <= "2002"
            "iyr" -> validYearFormRegex.matches(field.second) && field.second >= "2010" && field.second <= "2020"
            "eyr" -> validYearFormRegex.matches(field.second) && field.second >= "2020" && field.second <= "2030"
            "hgt" -> {
                if (validHeightFormRegex.matches(field.second)) {
                    val (value, unit) = validHeightFormRegex.find(field.second)!!.destructured
                    if (unit == "cm") value >= "150" && value <= "193" else value >= "59" && value <= "76"
                }
                else false
            }
            "hcl" -> validHairColorFormRegex.matches(field.second)
            "ecl" -> validEyeColorFormRegex.matches(field.second)
            "pid" -> validPassportIdFormRegex.matches(field.second)
            else -> true
        } }
    }
}