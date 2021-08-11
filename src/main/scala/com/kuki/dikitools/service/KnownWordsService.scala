package com.kuki.dikitools.service

import java.io.{BufferedWriter, FileWriter}

import com.kuki.dikitools.Word
import zio.ZIO
import zio.blocking.{Blocking, effectBlocking}

import scala.io.{Codec, Source}

object KnownWordsService {

  val FILE_NAME = "words"

  def loadWords(): ZIO[Blocking, Throwable, Set[Word]] = effectBlocking {
    Source.fromResource(FILE_NAME)(Codec.UTF8).getLines.toSet
  }

  def saveWords(words: Set[Word]): ZIO[Blocking, Throwable, Unit] = effectBlocking {
    val writer = new BufferedWriter(new FileWriter(s"src/main/resources/$FILE_NAME"))
    writer.write(words.toList.sorted.mkString("\n"))
    writer.close()
  }

  def updateWords(words: Set[Word], newWord: Word): ZIO[Blocking, Throwable, Unit] =
    if (words.contains(newWord)) ZIO.succeed(()) else saveWords(words + newWord)
}
