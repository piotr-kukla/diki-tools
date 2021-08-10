name := "diki-tools"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies += "dev.zio" %% "zio" % "1.0.9"
libraryDependencies ++= List(
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.3.7",
  "com.softwaremill.sttp.client3" %% "circe" % "3.3.7",
  "io.circe" %% "circe-generic" % "0.14.1",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.1"
)
