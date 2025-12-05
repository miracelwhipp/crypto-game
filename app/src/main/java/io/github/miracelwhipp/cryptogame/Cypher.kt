package io.github.miracelwhipp.cryptogame

import java.util.Locale
import kotlin.random.Random


class Cypher private constructor(
    val substitution: Map<Char, Char>,
    val reverseSubstitution: Map<Char, Char>
) {

    fun encrypt(text: String): String {

        return applySubstitution(text, substitution)
    }

    fun decrypt(text: String): String {

        return applySubstitution(text, reverseSubstitution)
    }

    fun isDecrypted(encrypted: Char, decrypted: Char): Boolean {

        return substitution[decrypted] == decrypted
    }

    private fun applySubstitution(
        text: String, subst: Map<Char, Char>
    ): String =
        text.map { character -> subst[character] ?: character }.joinToString("")

    fun reduce(decrypted: Char): Cypher {

        val mutableSubstitution = substitution.toMutableMap()

        val encrypted = mutableSubstitution.remove(decrypted)

        val mutableReverseSubstitution = reverseSubstitution.toMutableMap()
        mutableReverseSubstitution.remove(encrypted)

        return Cypher(
            mutableSubstitution.toMap(),
            mutableSubstitution.toMap()
        )
    }


    companion object {

        // TODO: more international symbols. at least danish
        const val CHARACTERS_TO_ENCODE_LOWERCASE = "abcdefghijklmnopqrstuvwxyzäöüß"

        val CHARACTERS_TO_ENCODE =
            (CHARACTERS_TO_ENCODE_LOWERCASE + CHARACTERS_TO_ENCODE_LOWERCASE.uppercase(Locale.ROOT)).toSet()

        fun randomKey(): Cypher {

            val substitutionTarget = CHARACTERS_TO_ENCODE.toSet().toMutableList()

            val substitution = mutableMapOf<Char, Char>()
            val reverseSubstitution = mutableMapOf<Char, Char>()

            for (sourceCharacter in CHARACTERS_TO_ENCODE) {

                if (substitution.keys.contains(sourceCharacter)) {

                    continue
                }

                val index = Random.nextInt(substitutionTarget.size)

                val targetCharacter = substitutionTarget.removeAt(index)
                substitutionTarget.remove(sourceCharacter)

                substitution[sourceCharacter] = targetCharacter
                reverseSubstitution[targetCharacter] = sourceCharacter

                substitution[targetCharacter] = sourceCharacter
                reverseSubstitution[sourceCharacter] = targetCharacter
            }

            return Cypher(
                substitution,
                reverseSubstitution
            )
        }

        fun newCypherKey(substitution: Map<Char, Char>): Cypher {

            return Cypher(
                substitution,
                substitution.map { Pair(it.value, it.key) }.toMap()
            )
        }
    }
}