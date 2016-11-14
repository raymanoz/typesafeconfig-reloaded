organization := "com.raymanoz.reloaded"

name := "reloaded"

scalaVersion := "2.12.0"

version := Option(System.getProperty("version")).getOrElse("dev.build")

publishTo := Some(Resolver.file("file", new File("target")))

resolvers += "Bodar Repo" at "http://repo.bodar.com/"

libraryDependencies ++= Seq(
  "com.typesafe"               %  "config"      % "1.3.1",
  "org.scalatest"              %% "scalatest"   % "3.0.0"  % "test",
  "com.googlecode.totallylazy" %  "totallylazy" % "1.85"   % "test"
)
