class Day22 : Day() {
    override val label: String get() = "22"

    private val initialDecks by lazy {
        fun String.toDeck(): List<Int> = this.lineSequence().drop(1).mapNotNull { it.toIntOrNull() }.toList()
        val split = input.split("\r\n\r\n")
        split[0].toDeck() to split[1].toDeck()
    }

    private fun List<Int>.privateHash(): Int = (this.size downTo 1).zip(this).sumBy { it.first * it.second }

    data class Situation(val zero: List<Int>, val one: List<Int>) {
        private fun List<Int>.privateHash(): Int = (this.size downTo 1).zip(this).sumBy { it.first * it.second }
        private val hashZero: Int = zero.privateHash()
        private val hashOne: Int = one.privateHash()
        val hash: Int = hashZero * hashOne

        override fun hashCode(): Int =
            hash

        override fun equals(other: Any?): Boolean =
            if (other is Situation)  other.zero == this.zero && other.one == this.one
            else false
    }

    val alreadyPlayedGames = mutableMapOf<Situation, Situation>()

    override fun taskZeroLogic(): String {
        val zerothPlayerDeck = initialDecks.first.toMutableList()
        val firstPlayerDeck = initialDecks.second.toMutableList()

        while (zerothPlayerDeck.any() && firstPlayerDeck.any()) {
            if (zerothPlayerDeck.first() > firstPlayerDeck.first()) {
                zerothPlayerDeck.add(zerothPlayerDeck.first())
                zerothPlayerDeck.add(firstPlayerDeck.first())
            }
            else {
                firstPlayerDeck.add(firstPlayerDeck.first())
                firstPlayerDeck.add(zerothPlayerDeck.first())
            }
            zerothPlayerDeck.removeAt(0)
            firstPlayerDeck.removeAt(0)
        }

        val winningDeck = if(zerothPlayerDeck.any()) zerothPlayerDeck else firstPlayerDeck

        return winningDeck.privateHash().toString()
    }

    fun recursiveCombat(gameSituation: Situation): Situation {
        fun round(roundSituation: Situation): Situation {
            val zerothFirst = roundSituation.zero.first()
            val firstFirst = roundSituation.one.first()
            val zeroWon = if(zerothFirst <= roundSituation.zero.size - 1 &&
                firstFirst <= roundSituation.one.size - 1) {
                val situation = Situation(zero = roundSituation.zero.drop(1).take(zerothFirst), one = roundSituation.one.drop(1).take(firstFirst))
                val resultSituation = recursiveCombat(situation)
                alreadyPlayedGames[situation] = resultSituation
                resultSituation.zero.any()
            }
            else zerothFirst > firstFirst
            return if (zeroWon) Situation(roundSituation.zero.drop(1) + zerothFirst + firstFirst, roundSituation.one.drop(1))
            else Situation(roundSituation.zero.drop(1), roundSituation.one.drop(1) + firstFirst + zerothFirst)
        }

        var situation = gameSituation

        if (alreadyPlayedGames.containsKey(situation))
            return alreadyPlayedGames[situation]!!

        val alreadyPlayed = mutableMapOf(situation.hash to mutableListOf(situation))

        while (situation.zero.any() && situation.one.any()) {

            situation = round(situation)
            if (alreadyPlayed.containsKey(situation.hash) &&
                alreadyPlayed[situation.hash]?.any { it == situation } == true) {
                return Situation(zero = situation.zero, one = listOf())
            }
            else if (alreadyPlayed.containsKey(situation.hash)) alreadyPlayed[situation.hash]?.add(situation)
            else alreadyPlayed[situation.hash] = mutableListOf(situation)
        }

        return situation
    }

    override fun taskOneLogic(): String {
        val pair = recursiveCombat(Situation(zero = initialDecks.first, one = initialDecks.second))
        val winningDeck = if (pair.zero.any()) pair.zero else pair.one
        return winningDeck.privateHash().toString()
    }
}