import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day by lazy { Day12() }
    @Test
    fun zero() = assertEquals("no solution found", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("no solution found", day.taskOneLogic())
}