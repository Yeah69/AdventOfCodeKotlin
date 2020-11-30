import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day by lazy { Day02() }
    @Test
    fun zero() = assertEquals("483", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("482", day.taskOneLogic())
}