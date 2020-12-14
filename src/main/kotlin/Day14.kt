import java.lang.Exception

fun String.toMaskValue(): ULong = this
    .replace("_", "")
    .reversed()
    .asSequence()
    .mapIndexed { i, c -> Pair(i, c) }
    .filter { p -> p.second == '1' }
    .map { p -> 1uL.shl(p.first) }
    .sum()

class Day14 : Day() {
    override val label: String get() = "14"

    interface Instruction {
        fun execute(mask: Mask, map: MutableMap<ULong, ULong>): Mask
    }

    abstract class Mask(val mask: String) : Instruction {
        override fun execute(mask: Mask, map: MutableMap<ULong, ULong>): Mask = this

        abstract fun transform(value: String): String

        protected fun transformInner(value: String, map: (Pair<Char, Char>) -> Char): String =
            mask.zip(value).map(map).joinToString("")
    }

    class MaskZero(mask1: String) : Mask(mask1) {
        override fun transform(value: String): String = transformInner(value) {
            (c1, c2) -> when (c1) {
                '0' -> '0'
                '1' -> '1'
                else -> c2 } }
    }

    class MaskOne(mask1: String) : Mask(mask1) {
        override fun transform(value: String): String = transformInner(value) {
            (c1, c2) -> when (c1) {
                '0' -> c2
                '1' -> '1'
                else -> 'X' } }
    }

    interface MemoryWrite : Instruction

    data class MemoryWriteZero(val address: String, val value: String) : MemoryWrite {
        override fun execute(mask: Mask, map: MutableMap<ULong, ULong>): Mask {
            map[address.toMaskValue()] = mask.transform(value).toMaskValue()
            return mask
        }
    }

    data class MemoryWriteOne(val address: String, val value: String) : MemoryWrite {

        private fun recursive(text: String) : Sequence<String> =
            if (text.contains('X').not())
                sequenceOf(text)
            else
                recursive(text.replaceFirst('X', '0')) + recursive(text.replaceFirst('X', '1'))

        override fun execute(mask: Mask, map: MutableMap<ULong, ULong>): Mask {
            recursive(mask.transform(address)).forEach { map[it.toMaskValue()] = value.toMaskValue() }
            return mask
        }
    }

    class Program(private val instructions: List<Instruction>) {
        private var mask: Mask = MaskZero("111111111111111111111111111111111111")

        val map = mutableMapOf<ULong, ULong>()

        fun run(): ULong {
            instructions.forEach { mask = it.execute(mask, map) }
            return map.values.sum()
        }
    }

    private val maskRegex: Regex = """^mask = ([01X]{36})$""".toRegex()

    private val memRegex: Regex = """^mem\[([0-9]+)] = ([0-9]+)$""".toRegex()

    private fun getInstruction(
        createMemoryWrite: (String, String) -> MemoryWrite,
        createMask: (String) -> Mask): List<Instruction> =
        input
            .lineSequence()
            .mapNotNull {
                val memMatch = memRegex.matchEntire(it)
                if (memMatch != null) {
                    val (address, value) = memMatch.destructured
                    val uLValue = value.toULong()
                    val textValue = (35 downTo 0)
                        .asSequence()
                        .map { if((1uL.shl(it).and(uLValue).shr(it)) == 1uL) '1' else '0' }
                        .joinToString("")
                    val uLaddressValue = address.toULong()
                    val textaddressValue = (35 downTo 0)
                        .asSequence()
                        .map { if((1uL.shl(it).and(uLaddressValue).shr(it)) == 1uL) '1' else '0' }
                        .joinToString("")
                    createMemoryWrite(textaddressValue, textValue) }
                else {
                    val (mask) = maskRegex.find(it)?.destructured ?: throw Exception()
                    createMask(mask) } }
            .toList()

    private val programZero: Program
        get() =  Program(getInstruction(::MemoryWriteZero, ::MaskZero))

    private val programOne: Program
        get() =  Program(getInstruction(::MemoryWriteOne, ::MaskOne))

    override fun taskZeroLogic(): String = programZero.run().toString()

    override fun taskOneLogic(): String = programOne.run().toString()
}