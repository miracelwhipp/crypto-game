package io.github.miracelwhipp.cryptogame

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testSomeSentences() {

        val candidate = TextSource.DEFAULT

        val sentences = candidate.getSentences(candidate.texts[0], 1000, 400)

        assertEquals(sentences, "Als der Vater einmal mit seinem invaliden Bein die Treppe herunterfiel, klatschte der Sohn wie toll in die Hände – worauf er weglief. Kam er nach einer Abstrafung mit gedunsenem Gesicht und unter Geheul an der Werkstätte vorbei, dann lachten die Arbeiter. Sofort aber streckte Diederich nach ihnen die Zunge aus und stampfte. Er war sich bewußt: „Ich habe Prügel bekommen, aber von meinem Papa. Ihr wäret froh, wenn ihr auch Prügel von ihm bekommen könntet.")
    }
}