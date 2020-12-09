import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day by lazy { Day14() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}