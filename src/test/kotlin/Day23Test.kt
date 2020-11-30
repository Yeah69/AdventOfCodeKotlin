import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day by lazy { Day23() }
    @Test
    fun zero() = assertEquals("89573246", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("2029056128", day.taskOneLogic())
}