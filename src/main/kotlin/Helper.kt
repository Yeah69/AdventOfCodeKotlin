var noSolutionFound = "no solution found"

fun <T> Sequence<T>.allPairs(): Sequence<Pair<T, T>> {
    val iterable = this
    return sequence {
        val iterated = mutableListOf<T>()
        for (i: T in iterable) {
            for (j: T in iterated) {
                yield(Pair(i, j))
            }
            iterated.add(i)
        }
    }
}