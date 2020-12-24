import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day by lazy { Day24() }
    @Test
    fun zero() = assertEquals("497", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("4156", day.taskOneLogic())
}