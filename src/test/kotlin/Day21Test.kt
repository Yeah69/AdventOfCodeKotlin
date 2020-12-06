import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day by lazy { Day21() }
    @Test
    fun zero() = assertEquals("no solution found", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("no solution found", day.taskOneLogic())
}