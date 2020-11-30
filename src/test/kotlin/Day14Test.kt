import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day by lazy { Day14() }
    @Test
    fun zero() = assertEquals("8566770985168", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("4832039794082", day.taskOneLogic())
}