import java.lang.Exception

class Day13 : Day() {
    override val label: String get() = "13"

    data class Bus(val id: Long, val offset: Long)

    data class Situation(val timestamp: Long, val busses: List<Bus>)

    private val situation by lazy {
        val lines = input.lines()
        val earliestPossibleDeparture = lines[0].toLongOrNull() ?: throw Exception()
        val busses = lines[1]
            .splitToSequence(',')
            .mapIndexed { i, s -> Pair(i.toLong(), s) }
            .filter { it.second != "x" }
            .map { Bus(it.second.toLong(), it.first) }
            .toList()
        Situation(earliestPossibleDeparture, busses)
    }

    override fun taskZeroLogic(): String {
        val earliestBusAndWait = situation
            .busses
            .asSequence()
            .map { Pair(it, it.id - (situation.timestamp % it.id)) }
            .minByOrNull { it.second } ?: throw Exception()
        return (earliestBusAndWait.first.id * earliestBusAndWait.second).toString()
    }

    private fun Bus.fits(timestamp: Long): Boolean = (timestamp + this.offset) % this.id == 0L

    override fun taskOneLogic(): String = situation
        .busses
        .fold(Bus(1L, 0L)) { curr, bus ->
            Bus(curr.id.lcm(bus.id),
                generateSequence(curr.offset) { it + curr.id }.first { bus.fits(it) }) }
        .offset
        .toString()

    private fun Long.gcd(other: Long): Long {
        var a = this
        var b = other
        while (b > 0) {
            val temp = b
            b = a % b
            a = temp
        }
        return a
    }

    private fun Long.lcm(other: Long): Long = this * (other / this.gcd(other))
}