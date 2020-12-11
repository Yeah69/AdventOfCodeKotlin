import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day by lazy { Day11() }
    @Test
    fun zero() = assertEquals("2299", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("2047", day.taskOneLogic())
}