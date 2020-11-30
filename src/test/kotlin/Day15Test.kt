import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day by lazy { Day15() }
    @Test
    fun zero() = assertEquals("1373", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("112458", day.taskOneLogic())
}