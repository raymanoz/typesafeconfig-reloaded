shavenMavenTask

organization := "com.raymanoz.reloaded"

name := "reloaded"

scalaVersion := "2.12.0"

version := Option(System.getProperty("version")).getOrElse("dev.build")

publishTo := Some(Resolver.file("file", new File("target")))

pomExtra := <dependencies>
  <dependency>
    <groupId>com.typesafe</groupId>
    <artifactId>config</artifactId>
    <version>1.3.1</version>
  </dependency>
</dependencies>