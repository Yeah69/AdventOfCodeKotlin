import kotlin.system.measureTimeMillis

interface Day {
    fun taskZeroLogic(input: String): String
    fun taskOneLogic(input: String): String
    fun taskZeroExecution(input: String) {
        var answer: String
        val timeInMillis = measureTimeMillis {
            answer = taskZeroLogic(input)
        }
        println("Answer Zero = $answer")
        println("(The task took $timeInMillis ms)")
    }
    fun taskOneExecution(input: String) {
        var answer: String
        val timeInMillis = measureTimeMillis {
            answer = taskOneLogic(input)
        }
        println("Answer One = $answer")
        println("(The task took $timeInMillis ms)")
    }
    fun execute(input: String) {
        println("Input =")
        println(input)
        val timeInMillis = measureTimeMillis {
            taskZeroExecution(input)
            taskOneExecution(input)
        }
        println("(The day took $timeInMillis ms in total)")
    }
}