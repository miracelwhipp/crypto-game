package io.github.miracelwhipp.cryptogame

import android.content.Context
import io.github.miracelwhipp.cryptogame.TextSource
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class TextSource private constructor(
    val texts: List<String>,
    val minimumTextSize: Int,
    val maximumTextSize: Int
) {

    fun getRandomSentences(): String {

        val textSource = texts.random()

        val resultSizeMoreOrLess = Random.nextInt(minimumTextSize, maximumTextSize + 1)

        val randomPosition = Random.nextInt(textSource.length - resultSizeMoreOrLess - 2) + 1

        return getSentences(textSource, randomPosition, resultSizeMoreOrLess)
    }

    fun getSentences(
        textSource: String,
        randomPosition: Int,
        resultSizeMoreOrLess: Int
    ): String {

        val startOfSentences = textSource.lastIndexOf('.', randomPosition - 1)

        val endOfSentences = textSource.indexOf('.', randomPosition + resultSizeMoreOrLess + 1)

        return (if (startOfSentences < 0) {

            if (endOfSentences < 0) {

                textSource

            } else {

                textSource.take(endOfSentences + 1)

            }

        } else {

            if (endOfSentences < 0) {

                textSource.substring(startOfSentences + 1)

            } else {

                textSource.substring(startOfSentences + 1, endOfSentences + 1)
            }
        })
            .trim()
            .replace(Regex("[\r\n]"), " ")
            .replace(Regex("[\t ]+"), " ")
    }

    companion object {

        var DEF: TextSource? = null

        fun defaultSource(context: Context): TextSource {

            if (DEF != null) {

                return DEF!!
            }

            val source1 = context.assets.open("untertan.txt")
                .bufferedReader(StandardCharsets.UTF_8)
                .use { it.readText() }


            DEF = TextSource(listOf(source1), 50, 300)

            return DEF!!
        }
    }
}