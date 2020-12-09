import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day by lazy { Day20() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}