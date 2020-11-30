import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    private val day by lazy { Day04() }
    @Test
    fun zero() = assertEquals("190", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("121", day.taskOneLogic())
}