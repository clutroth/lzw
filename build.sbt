name := "lzw3"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
libraryDependencies += "commons-io" % "commons-io" % "2.5" % "test"
test in assembly := {}
assemblyJarName in assembly := "lzw.jar"
mainClass in assembly := Some("pl.edu.pw.ee.decker.Main")
