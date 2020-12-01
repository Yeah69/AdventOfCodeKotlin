import java.io.File
import kotlin.system.measureTimeMillis

abstract class Day {
    protected abstract val number: String

    protected abstract fun taskZeroLogic(): String

    protected abstract fun taskOneLogic(): String

    private fun taskExecution(logic: () -> String, answerLabel: String)
    {
        var answer: String
        val timeInMillis = measureTimeMillis {
            answer = logic()
        }
        println("Answer $answerLabel = $answer")
        println("(The task took $timeInMillis ms)")
    }

    private fun taskZeroExecution() = taskExecution(::taskZeroLogic, "Zero")

    private fun taskOneExecution() = taskExecution(::taskOneLogic, "One")

    private var inputInner: String? = null
    protected val input: String
        get() {
            if (inputInner == null) {
                inputInner = File("input\\2020\\day$number.txt").readText()
            }
            return inputInner ?: ""
        }

    fun execute() {
        println("Input =")
        println(input)
        val timeInMillis = measureTimeMillis {
            taskZeroExecution()
            taskOneExecution()
        }
        println("(The day took $timeInMillis ms in total)")
    }
}