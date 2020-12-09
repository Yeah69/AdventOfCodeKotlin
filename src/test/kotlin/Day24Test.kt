import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day by lazy { Day24() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}