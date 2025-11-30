package io.github.miracelwhipp.cryptogame

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

        val TEXTS = listOf("Diederich Heßling war ein weiches Kind, das am liebsten träumte,\n" +
                "sich vor allem fürchtete und viel an den Ohren litt. Ungern verließ\n" +
                "er im Winter die warme Stube, im Sommer den engen Garten,\n" +
                "der nach den Lumpen der Papierfabrik roch und über dessen\n" +
                "Goldregen- und Fliederbäumen das hölzerne Fachwerk der alten\n" +
                "Häuser stand. Wenn Diederich vom Märchenbuch, dem geliebten\n" +
                "Märchenbuch, aufsah, erschrak er manchmal sehr. Neben ihm\n" +
                "auf der Bank hatte ganz deutlich eine Kröte gesessen, halb so\n" +
                "groß wie er selbst! Oder an der Mauer dort drüben stak bis zum\n" +
                "Bauch in der Erde ein Gnom und schielte her!\n" +
                "Fürchterlicher als Gnom und Kröte war der Vater, und\n" +
                "obendrein sollte man ihn lieben. Diederich liebte ihn. Wenn er\n" +
                "genascht oder gelogen hatte, drückte er sich so lange schmatzend\n" +
                "und scheu wedelnd am Schreibpult umher, bis Herr Heßling\n" +
                "etwas merkte und den Stock von der Wand nahm. Jede nicht\n" +
                "herausgekommene Untat mischte in Diederichs Ergebenheit und\n" +
                "Vertrauen einen Zweifel. Als der Vater einmal mit seinem\n" +
                "invaliden Bein die Treppe herunterfiel, klatschte der Sohn wie\n" +
                "toll in die Hände – worauf er weglief.\n" +
                "Kam er nach einer Abstrafung mit gedunsenem Gesicht und\n" +
                "unter Geheul an der Werkstätte vorbei, dann lachten die Arbeiter.\n" +
                "Sofort aber streckte Diederich nach ihnen die Zunge aus und\n" +
                "stampfte. Er war sich bewußt: „Ich habe Prügel bekommen, aber\n" +
                "von meinem Papa. Ihr wäret froh, wenn ihr auch Prügel von ihm\n" +
                "bekommen könntet. Aber dafür seid ihr viel zu wenig.“ \n" +
                "Er bewegte sich zwischen ihnen wie ein launenhafter Pascha;\n" +
                "drohte ihnen bald, es dem Vater zu melden, daß sie sich\n" +
                "Bier holten, und bald ließ er kokett aus sich die Stunde\n" +
                "herausschmeicheln, zu der Herr Heßling zurückkehren sollte. Sie\n" +
                "waren auf der Hut vor dem Prinzipal: er kannte sie, er hatte selbst\n" +
                "gearbeitet. Er war Büttenschöpfer gewesen in den alten Mühlen,\n" +
                "wo jeder Bogen mit der Hand geformt ward; hatte dazwischen\n" +
                "alle Kriege mitgemacht und nach dem letzten, als jeder Geld\n" +
                "fand, eine Papiermaschine kaufen können. Ein Holländer und\n" +
                "eine Schneidemaschine vervollständigten die Einrichtung. Er\n" +
                "selbst zählte die Bogen nach. Die von den Lumpen abgetrennten\n" +
                "Knöpfe durften ihm nicht entgehen. Sein kleiner Sohn ließ\n" +
                "sich oft von den Frauen welche zustecken, dafür, daß er die\n" +
                "nicht angab, die einige mitnahmen. Eines Tages hatte er so\n" +
                "viele beisammen, daß ihm der Gedanke kam, sie beim Krämer\n" +
                "gegen Bonbons umzutauschen. Es gelang – aber am Abend\n" +
                "kniete Diederich, indes er den letzten Malzzucker zerlutscht,\n" +
                "sich ins Bett und betete, angstgeschüttelt, zu dem schrecklichen\n" +
                "lieben Gott, er möge das Verbrechen unentdeckt lassen. Er\n" +
                "brachte es dennoch an den Tag. Dem Vater, der immer nur\n" +
                "methodisch, Ehrenfestigkeit und Pflicht auf dem verwitterten\n" +
                "Unteroffiziersgesicht, den Stock geführt hatte, zuckte diesmal\n" +
                "die Hand, und in die eine Bürste seines silberigen Kaiserbartes\n" +
                "lief, über die Runzeln hüpfend, eine Träne. „Mein Sohn hat\n" +
                "gestohlen“, sagte er außer Atem, mit dumpfer Stimme, und sah\n" +
                "sich das Kind an wie einen verdächtigen Eindringling. „Du\n" +
                "betrügst und stiehlst. Du brauchst nur noch einen Menschen\n" +
                "totzuschlagen.“\n" +
                "Frau Heßling wollte Diederich nötigen, vor dem Vater\n" +
                "hinzufallen und ihn um Verzeihung zu bitten, weil der Vater\n" +
                "seinetwegen geweint habe! Aber Diederichs Instinkt sagte ihm,\n" +
                "daß dies den Vater nur noch mehr erbost haben würde. Mit\n" +
                "der gefühlsseligen Art seiner Frau war Heßling durchaus nicht\n" +
                "einverstanden. Sie verdarb das Kind fürs Leben. Übrigens\n" +
                "ertappte er sie geradeso auf Lügen wie den Diedel. ")

        val DEFAULT = TextSource(TEXTS, 50, 300)
    }
}