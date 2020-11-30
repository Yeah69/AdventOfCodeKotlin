import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day by lazy { Day16() }
    @Test
    fun zero() = assertEquals("29019", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("517827547723", day.taskOneLogic())
}