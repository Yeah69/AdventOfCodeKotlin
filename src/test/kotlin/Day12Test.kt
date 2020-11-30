import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day by lazy { Day12() }
    @Test
    fun zero() = assertEquals("1603", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("52866", day.taskOneLogic())
}