shavenMavenTask

organization := "com.raymanoz"

name := "typesafeconfig-reloaded"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

version := {
  import System.getProperty
  Option(getProperty("version")).getOrElse("dev.build")
}