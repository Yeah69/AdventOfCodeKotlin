import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day by lazy { Day07() }
    @Test
    fun zero() = assertEquals("192", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("12128", day.taskOneLogic())
}