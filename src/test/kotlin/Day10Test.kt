import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day by lazy { Day10() }
    @Test
    fun zero() = assertEquals("2574", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("2644613988352", day.taskOneLogic())
}