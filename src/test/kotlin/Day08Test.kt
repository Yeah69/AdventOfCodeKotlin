import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day by lazy { Day08() }
    @Test
    fun zero() = assertEquals("1394", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("1626", day.taskOneLogic())
}