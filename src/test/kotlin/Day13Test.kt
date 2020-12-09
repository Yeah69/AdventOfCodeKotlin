import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day by lazy { Day13() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}