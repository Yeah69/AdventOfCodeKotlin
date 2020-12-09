import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day by lazy { Day15() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}