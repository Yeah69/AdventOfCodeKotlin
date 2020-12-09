import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day by lazy { Day22() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}