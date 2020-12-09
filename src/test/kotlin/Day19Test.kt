import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day by lazy { Day19() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}