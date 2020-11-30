import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day by lazy { Day25() }
    @Test
    fun zero() = assertEquals("11707042", day.taskZeroLogic())
    @Test
    fun one() = assertEquals(nothingToDoHere, day.taskOneLogic())
}