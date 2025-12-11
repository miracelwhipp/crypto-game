package io.github.miracelwhipp.cryptogame

import android.content.Context
import java.util.Locale

class CryptoRiddle(
    var cypher: Cypher,
    var text: String,
    val original: String
) {

    fun canBeGuessed(character: Char): Boolean {

        return cypher.reverseSubstitution.containsKey(character)
    }

    fun guessCharacter(encrypted: Char, decrypted: Char): Boolean {

        if (cypher.substitution[decrypted] != encrypted) {

            return false
        }

        cypher = cypher.reduce(decrypted)
        text = cypher.encrypt(original)

        return true
    }

    fun isSolved(): Boolean {

        return cypher.substitution.isEmpty() || !cypher.substitution.values.any { text.contains(it) }
    }

    companion object {

        fun randomRiddle(context: Context): CryptoRiddle {

            var original = TextSource.defaultSource(context).getRandomSentences().lowercase(Locale.ROOT)

            val cypher = Cypher.randomKey()

            var index = 0

            // always start with an encrypted letter
            while (original.length > index && !cypher.substitution.containsKey(original[index])) {
                index++
            }

            original = original.substring(index)

            return riddle(
                cypher,
                original
            )
        }

        fun riddle(cypher: Cypher, decrypted: String): CryptoRiddle {
            return CryptoRiddle(
                cypher,
                cypher.encrypt(decrypted),
                decrypted
            )
        }
    }
}