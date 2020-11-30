import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day by lazy { Day18() }
    @Test
    fun zero() = assertEquals("3348222486398", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("43423343619505", day.taskOneLogic())
}