package com.kuki.dikitools

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._
import net.ruippeixotog.scalascraper.scraper.HtmlExtractor

object HtmlParserExample extends App {

  //https://index.scala-lang.org/ruippeixotog/scala-scraper/scala-scraper/2.2.1?target=_2.13

  val browser = JsoupBrowser()
  val doc = browser.get("https://www.diki.pl/slownik-angielskiego?q=slack")
  val divSentences = doc >> elementList(".exampleSentence")

  def extractSentence(divText: String, translation: String) =
    divText.substring(0, divText.indexOf(translation))

  divSentences.foreach(div => {
    val translation = div >> allText(".exampleSentenceTranslation")
    val sentence = extractSentence(div.text, translation);
    println(s"Sentence: $sentence")
    println(s"Translation: $translation")
  })
}
