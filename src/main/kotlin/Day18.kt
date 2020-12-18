class Day18 : Day() {
    override val label: String get() = "18"

    private interface Token

    private data class Number (val value: Long) : Token

    private enum class OpType { ADD, MUL }

    private data class Operator (val type: OpType) : Token

    private enum class ParType { OPENING, CLOSING }

    private data class Parentheses  (val type: ParType) : Token

    private val expressions by lazy { input.lineSequence().map { it.tokenize().toList() }.toList() }

    private fun String.tokenize(): Sequence<Token> {
        val expression = this
        return sequence {
            for (c in expression)
                when (c) {
                    '+' -> yield(Operator(OpType.ADD))
                    '*' -> yield(Operator(OpType.MUL))
                    '(' -> yield(Parentheses(ParType.OPENING))
                    ')' -> yield(Parentheses(ParType.CLOSING))
                    ' ' -> {}
                    else -> yield(Number(c.toString().toLong())) } }
    }

    private fun MutableList<Token>.evaluate(evaluateParenthesesFree: (MutableList<Token>) -> Number): Number {
        do {
            var foundSome = false
            var firstClosing = 0
            var lastOpening = 0
            var i = 0
            while (i < this.size) {
                if (this[i] is Parentheses){
                    val parentheses = this[i] as Parentheses
                    if (parentheses.type == ParType.OPENING) lastOpening = i
                    else {
                        firstClosing = i
                        break
                    }
                }
                i++
            }
            if (firstClosing != 0) {
                val number = evaluateParenthesesFree(this.subList(lastOpening + 1, firstClosing).toMutableList())
                this[lastOpening] = number
                ((lastOpening + 1)..firstClosing).forEach { _ -> this.removeAt(lastOpening + 1) }
                foundSome = true
            }
        } while (foundSome)

        return evaluateParenthesesFree(this)
    }

    private fun logic(evaluateParenthesesFree: (MutableList<Token>) -> Number): String =
        expressions.map { it.toMutableList().evaluate(evaluateParenthesesFree).value }.sum().toString()

    override fun taskZeroLogic(): String = logic { list ->
        while (list.size != 1) {
            val first = (list[0] as Number).value
            val second = (list[2] as Number).value
            val op = (list[1] as Operator).type
            list[0] = Number(when (op) {
                OpType.ADD -> first + second
                OpType.MUL -> first * second
            })
            list.removeAt(1)
            list.removeAt(1)
        }
        list[0] as Number
    }

    override fun taskOneLogic(): String = logic { list ->
        var i = 0
        while (i < list.size) {
            if (list[i] == Operator(OpType.ADD))
            {
                val first = (list[i - 1] as Number).value
                val second = (list[i + 1] as Number).value
                list[i - 1] = Number(first + second)
                list.removeAt(i)
                list.removeAt(i)
            }
            else i++
        }
        list.removeIf{ it == Operator(OpType.MUL) }
        list.fold(Number(1)) { prev, curr -> Number(prev.value * (curr as Number).value) }
    }
}