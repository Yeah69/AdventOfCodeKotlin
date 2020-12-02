class Day02 : Day() {
    override val number: String
        get() = "02"

    private val policyAndPasswordRegex: Regex = """(\d+)-(\d+) (.): (.*)""".toRegex()

    private data class PolicyAndPassword (val min: Int, val max: Int, val Char: Char, val password: String)

    // input parsing
    private var maybePolicyAndPasswords: List<PolicyAndPassword>? = null
    private val policyAndPasswords: List<PolicyAndPassword>
        get() {
            if (maybePolicyAndPasswords == null) {
                maybePolicyAndPasswords = input
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
                    .toList()
            }
            return maybePolicyAndPasswords ?: listOf()
        }

    override fun taskZeroLogic(): String {
        val countOfValidPasswords = policyAndPasswords
            .filter {
                val count = it.password.toCharArray().filter { c -> c == it.Char }.count()
                count >= it.min && count <= it.max
            }
            .count()
        return countOfValidPasswords.toString()
    }

    override fun taskOneLogic(): String {
        val countOfValidPasswords = policyAndPasswords
            .filter {
                val chars = it.password.toCharArray()
                val a = chars[it.min - 1]
                val b = chars[it.max - 1]
                a != b &&  (a == it.Char || b == it.Char)
            }
            .count()
        return countOfValidPasswords.toString()
    }
}