class Day23 : Day() {
    override val label: String get() = "23"

    private val initialCups by lazy { input.asSequence().mapNotNull { it.toString().toIntOrNull() }.toList() }

    class Node(val value: Int) {
        lateinit var next: Node
    }

    private fun circularlyLinkedCupsZero(): Triple<Node, Map<Int, Node>, Int> {
        val map = mutableMapOf<Int, Node>()
        val last = Node(initialCups.last())
        map[last.value] = last
        var prev = last
        for(value in initialCups.reversed().drop(1)) {
            val node = Node(value)
            map[node.value] = node
            node.next = prev
            prev = node
        }
        last.next = prev
        return Triple(prev, map, 9)
    }

    private fun circularlyLinkedCupsOne(): Triple<Node, Map<Int, Node>, Int> {
        val map = mutableMapOf<Int, Node>()
        val start = circularlyLinkedCupsZero().first
        var tail = start
        map[tail.value] = tail
        (1..8).forEach { _ ->
            tail = tail.next
            map[tail.value] = tail
        }
        val millionValue = 1_000_000
        val millionTail = Node(millionValue)
        map[millionTail.value] = millionTail
        val ten = (999_999 downTo 10)
            .fold(millionTail) { acc, i ->
                val next = Node(i)
                map[next.value] = next
                next.next = acc
                next }
        millionTail.next = start
        tail.next = ten
        return Triple(start, map, millionValue)
    }

    private fun Node.step(map: Map<Int, Node>, maxLabelValue: Int): Node {
        fun Int.nextValue(): Int = if (this == 1) maxLabelValue else this - 1
        val trio = this.next
        val trioTail = trio.next.next
        val nextNonTrio = trioTail.next
        this.next = nextNonTrio
        trioTail.next = trio
        var seekedValue = this.value.nextValue()
        while (seekedValue == trio.value || seekedValue == trio.next.value || seekedValue == trioTail.value)
            seekedValue = seekedValue.nextValue()
        val nodeToAppend = map[seekedValue]!!
        val nextToTrioTail = nodeToAppend.next
        nodeToAppend.next = trio
        trioTail.next = nextToTrioTail
        return this.next
    }

    fun logic(triple: Triple<Node, Map<Int, Node>, Int>, moveCount: Int): Node {
        val (start, map, maxLabelValue) = triple
        (1..moveCount).fold(start) { acc, _ -> acc.step(map, maxLabelValue) }
        return map[1]!!
    }

    override fun taskZeroLogic(): String {
        return (1 until 8)
            .scan(logic(circularlyLinkedCupsZero(), 100).next) { acc, _ -> acc.next }
            .map { it.value }.joinToString("")
    }
    override fun taskOneLogic(): String {
        val one = logic(circularlyLinkedCupsOne(), 10_000_000)
        return (one.next.value.toLong() * one.next.next.value.toLong()).toString()
    }
}