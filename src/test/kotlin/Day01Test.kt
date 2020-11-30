import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day by lazy { Day01() }
    @Test
    fun zero() = assertEquals("1010884", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("253928438", day.taskOneLogic())
}