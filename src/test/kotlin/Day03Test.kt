import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day by lazy { Day03() }
    @Test
    fun zero() = assertEquals("176", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("5872458240", day.taskOneLogic())
}