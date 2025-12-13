package io.github.miracelwhipp.cryptogame

import android.content.Context
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class TextSource constructor(
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

        private val assets = listOf(
            "3männleinimwalde.txt",
            "3spinnerinnen.txt",
            "7geißlein.txt",
            "7raben.txt",
            "12brüder.txt",
            "aschenputttel.txt",
            "bremerstadtmusikanten.txt",
            "brüderchenundschwesterchen.txt",
            "dornrösschen.txt",
            "frauholle.txt",
            "froschkönig.txt",
            "furcht.txt",
            "hänselundgretel.txt",
            "katzundmaus.txt",
            "lumpengesindel.txt",
            "marienkind.txt",
            "rapunzel.txt",
            "rotkäppchen.txt",
            "rumpelstilzchen.txt",
            "schneewittchen.txt",
            "spielmann.txt",
            "tapferesschneiderlein.txt",
            "tischlein.txt",
            "treuerjohannes.txt",
            "untertan.txt"
        )

        private var DEFAULT: TextSource? = null

        fun defaultSource(context: Context): TextSource {

            if (DEFAULT != null) {

                return DEFAULT!!
            }

            val sources = assets.map { asset -> context.assets.open(asset).bufferedReader(StandardCharsets.UTF_8)
                .use { it.readText() }
            }

            DEFAULT = TextSource(sources, 50, 300)

            return DEFAULT!!
        }
    }
}