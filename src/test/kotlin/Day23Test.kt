import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day by lazy { Day23() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}