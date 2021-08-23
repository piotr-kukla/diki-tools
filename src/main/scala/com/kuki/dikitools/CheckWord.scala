package com.kuki.dikitools

import com.kuki.dikitools.service._
import zio._
import zio.blocking.Blocking
import zio.console._

object CheckWord extends App {


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {


    val program: ZIO[Blocking with Console with WordChecker with KnownWords, Throwable, Unit] = for {
      _    <- putStrLn("Check world: ")
      word <- getStrLn
      translations <- checkWord(word)
      _    <- ZIO.foreach(translations)(_.print())
      _    <- updateKnownWords(word, translations)
    } yield ()

    program
      .provideCustomLayer(WordChecker.live ++ KnownWordsMemo.live)
      .repeat(Schedule.forever)
      .exitCode
  }
}
