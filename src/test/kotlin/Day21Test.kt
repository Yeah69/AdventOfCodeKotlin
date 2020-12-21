import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day by lazy { Day21() }
    @Test
    fun zero() = assertEquals("2230", day.taskZeroLogic())
    @Test
    fun one() = assertEquals("qqskn,ccvnlbp,tcm,jnqcd,qjqb,xjqd,xhzr,cjxv", day.taskOneLogic())
}