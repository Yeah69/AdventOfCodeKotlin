import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day by lazy { Day17() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}