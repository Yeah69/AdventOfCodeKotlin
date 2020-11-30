class Day02 : Day() {
    override val label: String get() = "02"

    private val policyAndPasswordRegex: Regex = """(\d+)-(\d+) (.): (.*)""".toRegex()

    private data class PolicyAndPassword (val min: Int, val max: Int, val Char: Char, val password: String)

    private val policyAndPasswords by lazy { input
        .lines()
        .asSequence()
        .mapNotNull { policyAndPasswordRegex.find(it) }
        .map {
            val (min, max, char, password) = it.destructured
            PolicyAndPassword(
                min.toInt(),
                max.toInt(),
                char.toCharArray()[0],
                password) }
        .toList() }

    private fun logic(isValidPassword: (PolicyAndPassword) -> Boolean): String {
        val countOfValidPasswords = policyAndPasswords
            .filter(isValidPassword)
            .count()
        return countOfValidPasswords.toString()
    }

    override fun taskZeroLogic(): String = logic {
        val count = it.password.toCharArray().filter { c -> c == it.Char }.count()
        count >= it.min && count <= it.max }

    override fun taskOneLogic(): String = logic {
        val chars = it.password.toCharArray()
        (chars[it.min - 1] == it.Char).xor(chars[it.max - 1] == it.Char) }
}