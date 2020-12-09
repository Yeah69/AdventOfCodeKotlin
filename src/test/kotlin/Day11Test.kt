import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day by lazy { Day11() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}