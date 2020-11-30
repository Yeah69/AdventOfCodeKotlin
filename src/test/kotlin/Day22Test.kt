import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day by lazy { Day22() }
    @Test
    fun zero() = assertEquals("32179", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("30498", day.taskOneLogic())
}