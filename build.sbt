name := "thebet"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

//libraryDependencies += "com.robrua" % "orianna" % "2.0.5"

play.Project.playJavaSettings
