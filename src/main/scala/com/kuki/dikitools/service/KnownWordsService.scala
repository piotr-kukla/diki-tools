package com.kuki.dikitools.service

import java.io.{BufferedWriter, FileWriter}

import com.kuki.dikitools.Word
import com.kuki.dikitools.model.Translation
import zio.ZIO
import zio.blocking.{Blocking, effectBlocking}

import scala.io.{Codec, Source}

object KnownWordsService {

  val WORDS_FILE_NAME = "words"
  val SENTENCES_FILE_NAME = "sentences"

  def updateKnownWords(newWord: Word, translations: List[Translation]): ZIO[Blocking, Throwable, Unit] =
    for {
      knownWords <- loadWords()
      _ <- if (knownWords.contains(newWord)) ZIO.succeed() else updateDatabase(newWord, translations)
    } yield ()

  private def loadWords(): ZIO[Blocking, Throwable, Set[Word]] = effectBlocking {
    Source.fromResource(WORDS_FILE_NAME)(Codec.UTF8).getLines.toSet
  }

  private def updateDatabase(word: Word, translations: List[Translation]): ZIO[Blocking, Throwable, Unit] =
    for {
      _ <- appendNewWord(word)
      _ <- appendNewSentences(translations)
    } yield ()

  private def appendNewWord(word: Word): ZIO[Blocking, Throwable, Unit] = effectBlocking {
    val writer = new BufferedWriter(new FileWriter(s"src/main/resources/$WORDS_FILE_NAME", true))
    writer.write(s"$word\n")
    writer.close()
  }

  private def appendNewSentences(translations: List[Translation]) = effectBlocking {
    val writer = new BufferedWriter(new FileWriter(s"src/main/resources/$SENTENCES_FILE_NAME", true))
    writer.write(translations.map(_.toCsv()).mkString("\n"))
    writer.close()
  }
}
