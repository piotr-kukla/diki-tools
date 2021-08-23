package com.kuki.dikitools

import com.kuki.dikitools.model.Translation
import zio.{Has, ZIO}
import zio.blocking.Blocking

package object service {

  type WordChecker = Has[WordChecker.Service]
  type KnownWords = Has[KnownWordsMemo.Service]

  def checkWord(word: Word): ZIO[Blocking with WordChecker, Throwable, List[Translation]] =
    ZIO.accessM(_.get[WordChecker.Service].checkWord(word))

  def updateKnownWords(newWord: Word, translations: List[Translation]): ZIO[Blocking with KnownWords, Throwable, Unit] =
    ZIO.accessM(_.get[KnownWordsMemo.Service].updateKnownWords(newWord, translations))

}
