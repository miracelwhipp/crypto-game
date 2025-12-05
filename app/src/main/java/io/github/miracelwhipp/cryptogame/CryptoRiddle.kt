package io.github.miracelwhipp.cryptogame

class CryptoRiddle(
    var cypher: Cypher,
    var text: String,
    val original: String
) {

    fun canBeGuessed(character: Char): Boolean {

        return cypher.substitution.containsKey(character)
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

        fun randomRiddle(): CryptoRiddle {

            var original = TextSource.DEFAULT.getRandomSentences()

            val cypher = Cypher.randomKey()

            var index = 0

            while (original.length > index && !cypher.reverseSubstitution.containsKey(original[index])) {
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