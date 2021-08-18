package com.kuki.dikitools.service

import com.kuki.dikitools.Word
import com.kuki.dikitools.model.Translation
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{allText, elementList}
import net.ruippeixotog.scalascraper.dsl.DSL._
import zio.{Layer, ZIO, ZLayer}
import zio.blocking.{Blocking, effectBlocking}

object WordChecker extends Serializable {

  trait Service extends Serializable {
    def checkWord(word: Word): ZIO[Blocking, Throwable, List[Translation]]
  }

  object Service {

    val browser = JsoupBrowser()

    val live: Service = (word: Word) => effectBlocking {

      def extractSentence(divText: String, translation: String) =
        divText.substring(0, divText.indexOf(translation))

      val doc = browser.get(s"https://www.diki.pl/slownik-angielskiego?q=$word")
      val divSentences = doc >> elementList(".exampleSentence")

      divSentences.map(div => {
        val translation = div >> allText(".exampleSentenceTranslation")
        val sentence = extractSentence(div.text, translation)
        Translation(word, sentence, translation)
      })
    }
  }

  val live: Layer[Nothing, WordChecker] =
    ZLayer.succeed(Service.live)
}
