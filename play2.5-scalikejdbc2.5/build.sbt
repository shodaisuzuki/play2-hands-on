name := """play2.5-scalikejdbc2.5"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % Test

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.193",
  "org.scalikejdbc" %% "scalikejdbc" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1"
)

scalikejdbcSettings

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
