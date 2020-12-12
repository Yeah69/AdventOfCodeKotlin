import java.lang.Exception
import kotlin.math.abs

class Day12 : Day() {
    override val label: String get() = "12"

    private data class Situation(val waypoint: Pair<Int, Int>, val shipPosition: Pair<Int, Int>)

    private fun Pair<Int, Int>.left(value: Int): Pair<Int, Int> =
        when ((value % 360) / 90) {
            0 -> this
            1 -> Pair(-this.second,  this.first)
            2 -> Pair(-this.first,  -this.second)
            3 -> Pair( this.second, -this.first)
            else -> this }

    private fun Pair<Int, Int>.right(value: Int): Pair<Int, Int> = this.left(360 - (value % 360))

    private fun Pair<Int, Int>.shiftPositionBy(offset: Pair<Int, Int>, factor: Int): Pair<Int, Int> =
        Pair(this.first  + (offset.first  * factor), this.second + (offset.second * factor))

    private val lineRegex: Regex = """^([NSEWLRF])([0-9]+)$""".toRegex()

    private val actions by lazy { input
        .lineSequence()
        .map {
            val (action, value) = lineRegex.find(it)?.destructured ?: throw Exception()
            Pair(action, value.toInt()) }
        .toList() }

    private fun logic(initialSituation: Situation, folding: (Situation, Pair<String, Int>) -> Situation): String {
        val finalSituation = actions.fold(initialSituation, folding)
        return (abs(finalSituation.shipPosition.first) + abs(finalSituation.shipPosition.second)).toString()
    }

    override fun taskZeroLogic(): String = logic(Situation(Pair(1, 0), Pair(0, 0))) { prevSituation, next ->
        val newWaypoint = when (next.first) {
            "N" -> Pair( 0,  1)
            "S" -> Pair( 0, -1)
            "W" -> Pair(-1,  0)
            "E" -> Pair( 1,  0)
            "L" -> prevSituation.waypoint.left(next.second)
            "R" -> prevSituation.waypoint.right(next.second)
            else -> prevSituation.waypoint
        }
        val facingWaypoint = when (next.first) {
            "L" -> newWaypoint
            "R" -> newWaypoint
            else -> prevSituation.waypoint
        }
        val newShipPosition =
            if (next.first != "L" && next.first != "R")
                prevSituation.shipPosition.shiftPositionBy(newWaypoint, next.second)
            else
                prevSituation.shipPosition
        Situation(facingWaypoint, newShipPosition) }

    override fun taskOneLogic(): String = logic(Situation(Pair(10, 1), Pair(0, 0))) { prevSituation, next ->
        val newWaypoint = when (next.first) {
            "N" -> Pair(prevSituation.waypoint.first,               prevSituation.waypoint.second + next.second)
            "S" -> Pair(prevSituation.waypoint.first,               prevSituation.waypoint.second - next.second)
            "W" -> Pair(prevSituation.waypoint.first - next.second, prevSituation.waypoint.second              )
            "E" -> Pair(prevSituation.waypoint.first + next.second, prevSituation.waypoint.second              )
            "L" -> prevSituation.waypoint.left(next.second)
            "R" -> prevSituation.waypoint.right(next.second)
            else -> prevSituation.waypoint
        }

        val newShipPosition =
            if (next.first == "F")
                prevSituation.shipPosition.shiftPositionBy(newWaypoint, next.second)
            else
                prevSituation.shipPosition

        Situation(newWaypoint, newShipPosition) }
}