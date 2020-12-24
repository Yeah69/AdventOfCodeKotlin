class Day24 : Day() {
    override val label: String get() = "24"

    private enum class Direction { EAST, SOUTHEAST, SOUTHWEST, WEST, NORTHWEST, NORTHEAST }

    private val initialBlackTiles by lazy { input
        .lineSequence()
        .map {
            var rest = it
            val instructions = mutableListOf<Direction>()
            while (rest.isBlank().not()) {
                var takeTwo = true
                when {
                    rest.startsWith("e" ) -> { instructions.add(Direction.EAST     ); takeTwo = false }
                    rest.startsWith("w" ) -> { instructions.add(Direction.WEST     ); takeTwo = false }
                    rest.startsWith("se") ->   instructions.add(Direction.SOUTHEAST)
                    rest.startsWith("sw") ->   instructions.add(Direction.SOUTHWEST)
                    rest.startsWith("ne") ->   instructions.add(Direction.NORTHEAST)
                    rest.startsWith("nw") ->   instructions.add(Direction.NORTHWEST) }
                rest = rest.substring(if(takeTwo) 2 else 1)
            }
            instructions
        }
        .map { it.fold(0 to 0) { acc, direction ->
            when (direction) {
                Direction.WEST ->      acc.first - 2 to acc.second
                Direction.EAST ->      acc.first + 2 to acc.second
                Direction.NORTHWEST -> acc.first - 1 to acc.second - 1
                Direction.NORTHEAST -> acc.first + 1 to acc.second - 1
                Direction.SOUTHWEST -> acc.first - 1 to acc.second + 1
                Direction.SOUTHEAST -> acc.first + 1 to acc.second + 1 } } }
        .groupBy { it }
        .filter { it.value.count() % 2 == 1 }
        .map { it.key }
        .toList()
    }

    override fun taskZeroLogic(): String = initialBlackTiles.count().toString()

    override fun taskOneLogic(): String = (1..100)
        .fold(initialBlackTiles.toHashSet()) { acc, _ ->
            fun Pair<Int, Int>.neighbors(): Sequence<Pair<Int, Int>> {
                val it = this
                return sequence {
                          yield(it.first - 1 to it.second - 1); yield(it.first + 1 to it.second - 1)
                    yield(it.first - 2 to it.second);                 yield(it.first + 2 to it.second)
                          yield(it.first - 1 to it.second + 1); yield(it.first + 1 to it.second + 1)
                }
            }
            val stayBlack = acc
                .filter {
                    val blackNeighbors = it.neighbors().filter { n -> acc.contains(n) }.count()
                    blackNeighbors == 1 || blackNeighbors == 2
                }
            val flipToBlack = acc
                .flatMap { it.neighbors() }
                .filterNot { acc.contains(it) }
                .filter { it.neighbors().filter { n -> acc.contains(n) }.count() == 2 }
            (stayBlack + flipToBlack).toHashSet()
        }
        .count()
        .toString()
}