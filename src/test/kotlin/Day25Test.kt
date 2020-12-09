import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day by lazy { Day25() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}