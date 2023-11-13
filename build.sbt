import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.devlike"
ThisBuild / organizationName := "devlike"


lazy val root = (project in file("."))
  .settings(
    name := "rog",
    libraryDependencies += munit % Test
  )

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-contrib" % "0.3.0"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.3"

fork := true