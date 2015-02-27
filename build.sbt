name := "thebet"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "joda-time" % "joda-time" % "2.3"
)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

play.Project.playJavaSettings
