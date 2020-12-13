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
        println("${ConsoleColor.BLUE_BOLD}Answer $answerLabel${ConsoleColor.RESET} =")
        println("${ConsoleColor.MAGENTA_BOLD}$answer${ConsoleColor.RESET}")
        println("(The task took ${ConsoleColor.CYAN_BOLD}$timeInMillis ms${ConsoleColor.RESET})")
        println()
    }

    private fun taskZeroExecution() = taskExecution(::taskZeroLogic, "Zero")

    private fun taskOneExecution() = taskExecution(::taskOneLogic, "One")

    protected val input by lazy { File("input${File.separatorChar}2020${File.separatorChar}day$label.txt").readText() }

    fun execute() {
        println()
        println("${ConsoleColor.BLUE_BOLD}Day ${ConsoleColor.MAGENTA_BOLD}$label${ConsoleColor.RESET}")
        println()
        println("${ConsoleColor.BLUE_BOLD}Input${ConsoleColor.RESET} =")
        println("${ConsoleColor.MAGENTA_BOLD}$input${ConsoleColor.RESET}")
        println()
        val timeInMillis = measureTimeMillis {
            taskZeroExecution()
            taskOneExecution()
        }
        println("(The ${ConsoleColor.BLUE_BOLD}day${ConsoleColor.RESET} ${ConsoleColor.MAGENTA_BOLD}$label${ConsoleColor.RESET} took ${ConsoleColor.CYAN_BOLD}$timeInMillis ms${ConsoleColor.RESET} in total)")
    }
}