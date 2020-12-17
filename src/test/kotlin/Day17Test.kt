import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day by lazy { Day17() }
    @Test
    fun zero() = assertEquals("384", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("2012", day.taskOneLogic())
}