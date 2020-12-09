import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day by lazy { Day09() }
    @Test
    fun zero() = assertEquals("25918798", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("3340942", day.taskOneLogic())
}