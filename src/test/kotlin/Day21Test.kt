import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day by lazy { Day21() }
    @Test
    fun zero() = assertEquals(noSolutionFound, day.taskZeroLogic())
    @Test
    fun one() = assertEquals(noSolutionFound, day.taskOneLogic())
}