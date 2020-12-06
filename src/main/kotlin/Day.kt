import java.io.File
import kotlin.system.measureTimeMillis

abstract class Day {
    protected abstract val label: String

    abstract fun taskZeroLogic(): String

    abstract fun taskOneLogic(): String

    private fun taskExecution(logic: () -> String, answerLabel: String)
    {
        var answer: String
        val timeInMillis = measureTimeMillis { answer = logic() }
        println("Answer $answerLabel = $answer")
        println("(The task took $timeInMillis ms)")
    }

    private fun taskZeroExecution() = taskExecution(::taskZeroLogic, "Zero")

    private fun taskOneExecution() = taskExecution(::taskOneLogic, "One")

    protected val input by lazy { File("input\\2020\\day$label.txt").readText() }

    fun execute() {
        println("Input =")
        println(input)
        val timeInMillis = measureTimeMillis {
            taskZeroExecution()
            taskOneExecution()
        }
        println("(The day $label took $timeInMillis ms in total)")
    }
}