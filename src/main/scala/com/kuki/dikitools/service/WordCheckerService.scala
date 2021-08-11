package com.kuki.dikitools.service

import com.kuki.dikitools.Word
import com.kuki.dikitools.model.Translation
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import zio.ZIO
import zio.blocking.{Blocking, effectBlocking}

object WordCheckerService {

  val browser = JsoupBrowser()

  def checkWord(word: Word): ZIO[Blocking, Throwable, List[Translation]] = effectBlocking {

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
