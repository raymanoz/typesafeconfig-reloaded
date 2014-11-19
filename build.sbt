shavenMavenTask

organization := "com.raymanoz.reloaded"

name := "reloaded"

scalaVersion := "2.11.4"

scalaBinaryVersion := "2.11"

version := Option(System.getProperty("version")).getOrElse("dev.build")

publishTo := Some(Resolver.file("file", new File("target")))

pomExtra := <dependencies>
  <dependency>
    <groupId>com.typesafe</groupId>
    <artifactId>config</artifactId>
    <version>1.2.1</version>
  </dependency>
</dependencies>