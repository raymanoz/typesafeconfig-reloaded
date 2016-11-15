package com.raymanoz.reloaded

import java.io.File

import com.googlecode.totallylazy.Files.write
import com.googlecode.totallylazy.Strings.bytes
import com.typesafe.config.ConfigException.UnresolvedSubstitution
import org.scalatest.FunSuite

import scala.util.Random

class FallbackConfigurationLoaderTest extends FunSuite {
  private def writeConfig(configFilename: String, content: String) = write(bytes(content), new File("target/scala-2.12/test-classes/" + configFilename))

  test("blows up if config file does not exist") {
    intercept[RuntimeException] {
      FallbackConfigurationLoader.load("doesNotExist")
    }
  }

  test("can get values from a config with fallback specified") {
    writeConfig("ref.conf", "var1=sheep")
    writeConfig("override.conf",
      """fallback=ref
        |var2=cheese""".stripMargin)

    assert(FallbackConfigurationLoader.load("override").getString("var2") === "cheese")
  }

  test("can get reference values from a config with fallback specified") {
    writeConfig("ref.conf", "var1=sheep")
    writeConfig("override.conf",
      """fallback=ref
        |var2=cheese""".stripMargin)

    assert(FallbackConfigurationLoader.load("override").getString("var1") === "sheep")
  }

  test("can get structured values") {
    writeConfig("config.conf",
      """lookup {
        |  value=puppies
        |}""".stripMargin)

    assert(FallbackConfigurationLoader.load("config").getString("lookup.value") === "puppies")
  }

  test("variables used in reference, and defined in reference, can be overriden in later configs") {
    writeConfig("ref.conf",
      """var=sheep
        |value=${var}""".stripMargin)
    writeConfig("override.conf",
      """fallback=ref
        |var=cheese""".stripMargin)

    assert(FallbackConfigurationLoader.load("override").getString("value") === "cheese")
  }

  test("cat get system property values") {
    System.setProperty("jabberwocky", "Jubjub bird")
    writeConfig("config.conf", "value=${jabberwocky}")

    assert(FallbackConfigurationLoader.load("config").getString("value") === "Jubjub bird")
  }

  test("cannot load if variable subsitution fails") {
    writeConfig("config.conf", "var=${missing}")

    intercept[UnresolvedSubstitution] {
      FallbackConfigurationLoader.load("config")
    }
  }
}
