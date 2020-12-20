import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day by lazy { Day20() }
    @Test
    fun zero() = assertEquals("108603771107737", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("2129", day.taskOneLogic())
}