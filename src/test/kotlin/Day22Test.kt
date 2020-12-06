import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day by lazy { Day22() }
    @Test
    fun zero() = assertEquals("no solution found", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("no solution found", day.taskOneLogic())
}