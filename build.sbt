name := "validation"

organization := "com.decaf"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.14" % "test"
)
