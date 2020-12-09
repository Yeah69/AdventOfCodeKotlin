import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day by lazy { Day12() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}