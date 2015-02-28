import com.ketalo.play.plugins.emberjs.EmberJsKeys

name := """sumbet"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.google.inject" % "guice" % "3.0",
  "com.robrua" % "orianna" % "2.1.0",
  "joda-time" % "joda-time" % "2.3",
  "com.typesafe.akka" %% "akka-actor" % "2.2.3",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)

emberJsVersion := "1.7.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
