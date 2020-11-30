import java.lang.Exception

class Day08 : Day() {
    override val label: String get() = "08"

    private val lineRegex: Regex = """^(.{3}) ([+\-][0-9]+)$""".toRegex()

    private fun createInstructions(from: String) = from
        .lineSequence()
        .map {
            val (instructionText, valueText) = lineRegex.find(it)?.destructured
                ?: throw Exception("Un-destructure-able")
            val value = valueText.toInt()
            val instruction = when (instructionText) {
                "acc" -> { acc: Int -> acc + value }
                else -> { acc: Int -> acc } }
            val offset = when (instructionText) {
                "jmp" -> value
                else -> 1 }
            Instruction(offset, instruction) }
        .toList()

    private val instructionLines by lazy { input.lines() }

    private val instructions by lazy { createInstructions(input) }

    private val alteredInstructionsSequence = sequence {
        for (i in 0 until instructionLines.count())
        {
            if (instructionLines[i].startsWith("nop") || instructionLines[i].startsWith("jmp"))
            {
                val newInstructions = instructionLines.toMutableList()
                newInstructions[i] = if (newInstructions[i].startsWith("nop"))
                        newInstructions[i].replace("nop", "jmp")
                    else newInstructions[i].replace("jmp", "nop")
                yield(createInstructions(newInstructions.joinToString("\r\n")))
            }
        }
    }

    private fun runToLoopOrTermination(instructions: List<Instruction>): Pair<Int, Boolean> {
        val program = Program(instructions)
        val executedInstructions = hashSetOf<Int>()
        do {
            executedInstructions.add(program.programCounter)
            program.executeNext()
        } while (program.terminated.not() &&
            executedInstructions.contains(program.programCounter).not())
        return Pair(program.accumulator, program.terminated)
    }

    override fun taskZeroLogic(): String = runToLoopOrTermination(instructions).first.toString()

    override fun taskOneLogic(): String = alteredInstructionsSequence
        .map { runToLoopOrTermination(it) }
        .filter { it.second }
        .map { it.first }
        .firstOrNull()
        ?.toString()
        ?: noSolutionFound


    data class Instruction (val offset: Int, val accAlteration: (Int) -> Int)

    class Program (private val instructions: List<Instruction>) {
        var accumulator = 0
            private set
        var programCounter = 0
            private set
        val terminated get() = programCounter >= instructions.count()

        fun executeNext() {
            if (terminated) return
            val instruction = instructions[programCounter]
            accumulator = instruction.accAlteration(accumulator)
            programCounter += instruction.offset
        }
    }
}