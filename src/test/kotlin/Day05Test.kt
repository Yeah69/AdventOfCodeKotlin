import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day by lazy { Day05() }
    @Test
    fun zero() = assertEquals("866", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("583", day.taskOneLogic())
}