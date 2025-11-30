package io.github.miracelwhipp.cryptogame

class CryptoRiddle(
    var cypher: Cypher,
    var text: String
) {

    fun canBeGuessed(character: Char): Boolean {

        return cypher.substitution.containsKey(character)
    }
    fun guessCharacter(encrypted:Char, decrypted: Char): Boolean {

        if (cypher.substitution[decrypted] != encrypted) {

            return false
        }

        text = text.replace(encrypted, decrypted)
        cypher = cypher.reduce(decrypted)

        return true
    }

    fun isSolved(): Boolean {

        return cypher.substitution.isEmpty()
    }

    companion object {

        fun randomRiddle(): CryptoRiddle {

            return riddle(
                Cypher.randomKey(),
                TextSource.DEFAULT.getRandomSentences()
            )
        }

        fun riddle(cypher: Cypher, decrypted: String): CryptoRiddle {
            return CryptoRiddle(
                cypher,
                cypher.encrypt(decrypted)
            )
        }
    }
}