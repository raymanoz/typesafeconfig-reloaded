shavenMavenTask

organization := "com.raymanoz"

name := "typesafeconfig-reloaded"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

version := {
  import System.getProperty
  Option(getProperty("version")).getOrElse("dev.build")
}

publishTo := Some(Resolver.file("file", new File("target")))

pomExtra := <dependencies>
  <dependency>
    <groupId>com.typesafe</groupId>
    <artifactId>config</artifactId>
    <version>1.2.1</version>
  </dependency>
</dependencies>