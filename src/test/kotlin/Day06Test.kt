import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day by lazy { Day06() }
    @Test
    fun zero() = assertEquals("6457", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("3260", day.taskOneLogic())
}