class Day17 : Day() {
    override val label: String get() = "17"

    private data class Quadruple(val x: Int, val y: Int, val z: Int, val w: Int)

    private fun <T> initialState(factory: (Int, Int) -> T) = input
        .lineSequence()
        .flatMapIndexed { y, line -> line
            .asSequence()
            .mapIndexed { x, c -> Pair(x, c) }
            .filter { (_, c) -> c == '#' }
            .map { (x, _) -> factory(x, y) } }
        .toHashSet()

    private val initialStateZero by lazy { initialState { x, y -> Triple(x, y, 0) } }

    private val initialStateOne by lazy { initialState { x, y -> Quadruple(x, y, 0, 0) } }

    private fun <T> HashSet<T>.cycle(neighborhood: (T) -> Sequence<T>): HashSet<T> = this
        .flatMap { neighborhood(it) }
        .distinct()
        .filter {
            val active = this.contains(it)
            val activeNeighbors = neighborhood(it)
                .filter { n -> n != it }
                .filter { n -> this.contains(n) }
                .count()
            active && activeNeighbors == 2 || activeNeighbors == 3
        }
        .toHashSet()

    private fun <T> logic(initialState: HashSet<T>, neighborhood: (T) -> Sequence<T>): String = (0 until 6)
        .fold(initialState) { prevState, _ -> prevState.cycle { neighborhood(it) } }
        .count()
        .toString()

    private fun Int.range(): IntRange = (this - 1)..(this + 1)

    override fun taskZeroLogic(): String = logic(initialStateZero) {
        val (x, y, z) = it
        sequence {
            for(x_ in x.range())
                for(y_ in y.range())
                    for(z_ in z.range())
                        yield (Triple(x_, y_, z_)) } }

    override fun taskOneLogic(): String = logic(initialStateOne) {
        val (x, y, z, w) = it
        sequence {
            for(x_ in x.range())
                for(y_ in y.range())
                    for(z_ in z.range())
                        for(w_ in w.range())
                            yield (Quadruple(x_, y_, z_, w_)) } }
}