import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day by lazy { Day19() }
    @Test
    fun zero() = assertEquals("224", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("436", day.taskOneLogic())
}