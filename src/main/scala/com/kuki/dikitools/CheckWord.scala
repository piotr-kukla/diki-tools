package com.kuki.dikitools

import com.kuki.dikitools.service.{KnownWordsService, WordCheckerService}
import zio._
import zio.console._

object CheckWord extends App {


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val program = for {
      _    <- putStrLn("Check world: ")
      word <- getStrLn
      translations <- WordCheckerService.checkWord(word)
      _    <- ZIO.foreach(translations)(_.print())
      _    <- KnownWordsService.updateKnownWords(word, translations)
    } yield ()

    program.repeat(Schedule.forever).exitCode
  }
}
