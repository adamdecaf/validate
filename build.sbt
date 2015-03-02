name := "validation"

organization := "com.decaf"

scalaVersion := "2.11.5"

crossScalaVersions ++= Seq("2.10.4", "2.11.5")

version := "1.0-SNAPSHOT"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps", "-Xlint", "-Xlog-free-terms", "-Xlog-free-types",
                      "-language:implicitConversions", "-language:higherKinds", "-language:existentials", "-language:postfixOps",
                      "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-inaccessible", "-unchecked")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.4.16" % "test"
)
