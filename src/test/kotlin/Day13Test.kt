import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day by lazy { Day13() }
    @Test
    fun zero() = assertEquals("2215", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("1058443396696792", day.taskOneLogic())
}