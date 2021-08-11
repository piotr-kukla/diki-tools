package com.kuki.dikitools

import com.kuki.dikitools.service.WordChecker
import sttp.client3.asynchttpclient.zio.{AsyncHttpClientZioBackend, SttpClient}
import zio._
import zio.console._

object CheckWord extends App {


  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val program = for {
      _    <- putStrLn("Check world: ")
      word <- getStrLn
      translations <- WordChecker.checkWord(word)
      _    <- ZIO.foreach(translations)(_.print())
    } yield ()

    program.exitCode
  }
}
