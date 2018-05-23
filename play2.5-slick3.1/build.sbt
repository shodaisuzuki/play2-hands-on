name := """play2-hands-on"""
organization := "play2-hands-on for bizreach"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "play2-hands-on for bizreach.controllers._"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.193",
  "com.typesafe.play" %% "play-slick" % "2.0.2"
)

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "play2-hands-on for bizreach.binders._"
