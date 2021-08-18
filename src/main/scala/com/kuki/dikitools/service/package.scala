package com.kuki.dikitools

import com.kuki.dikitools.model.Translation
import zio.{Has, ZIO}
import zio.blocking.Blocking

package object service {

  type WordChecker = Has[WordChecker.Service]

  def checkWord(word: Word): ZIO[Blocking with WordChecker, Throwable, List[Translation]] =
    ZIO.accessM(_.get[WordChecker.Service].checkWord(word))

}
