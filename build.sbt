name := """sumfriends"""

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
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.google.guava" % "guava" % "18.0"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

lazy val root = (project in file(".")).enablePlugins(PlayJava,SbtWeb)