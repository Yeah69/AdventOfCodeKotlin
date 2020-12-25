class Day25 : Day() {
    override val label: String get() = "25"

    private val publicKeys by lazy {
        val list = input.lineSequence().mapNotNull { it.toLongOrNull() }.toList()
        list[0] to list[1]
    }

    override fun taskZeroLogic(): String {
        val (myLoopSize, otherPublicKey) = generateSequence(1L) { (it * 7L) % 20201227L }
            .mapIndexed { i, value -> i to value }
            .filter { it.second == publicKeys.first || it.second == publicKeys.second }
            .map {
                if(it.second == publicKeys.first)
                    it.first to publicKeys.second
                else
                    it.first to publicKeys.first }
            .first()
        return (1..myLoopSize).fold(1L) { it, _ -> (it * otherPublicKey) % 20201227L }.toString()
    }
    override fun taskOneLogic(): String = nothingToDoHere
}