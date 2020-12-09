import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day by lazy { Day10() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}