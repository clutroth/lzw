name := "lzw3"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "commons-io" % "commons-io" % "2.5" % "test"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

assemblyJarName in assembly := "lzw.jar"
test in assembly := {}
mainClass in assembly := Some("pl.edu.pw.ee.decker.Main")
