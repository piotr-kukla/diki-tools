package com.kuki.dikitools.model

import com.kuki.dikitools.Word

import zio.console._

case class Translation(word: Word, sentence: String, translation: String) {

  def print() = putStrLn(s"$sentence $translation")
  def toCsv() = s"$word|$sentence|$translation|0"
}
