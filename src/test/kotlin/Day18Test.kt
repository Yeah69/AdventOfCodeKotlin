import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day by lazy { Day18() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}