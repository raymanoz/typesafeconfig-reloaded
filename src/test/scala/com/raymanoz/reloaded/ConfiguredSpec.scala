package com.raymanoz.reloaded

import java.io.{File, PrintWriter}

import org.scalatest.FunSuite

import scala.util.Success
import scala.util.Try

class ConfiguredSpec extends FunSuite {
   test("get optional value") {
     val config = writeConfig("tallahassee=banjo")
     assert(config.optional[String]("tallahassee") === Success(Some("banjo")))
     assert(config.optional[String]("columbus") === Success(None))
   }

   test("failure reported when incorrect type used") {
     val config = writeConfig("tallahassee=banjo")
     assert(config.optional[Int]("tallahassee").isFailure)
   }

   test("get a required value") {
     val config = writeConfig("tallahassee=banjo")
     assert(config.required[String]("tallahassee") === Success("banjo"))
   }

   test("fail when missing required property") {
     val config = writeConfig("tallahassee=banjo")
     assert(config.required[String]("columbus").isFailure)
   }

   test("fail when required property has no value") {
     val config = writeConfig( """tallahassee=""""")
     assert(config.required[String]("columbus").isFailure)
   }

   test("can get each type of property") {
     val newFile = writeFile("", "file.txt")
     val config = writeConfig(
       """int=0
         |string="foo"
         |boolean=true,
         |filename="target/scala-2.11/classes/file.txt"
       """.stripMargin)
     assert(config.required[Int]("int") === Success(0))
     assert(config.required[String]("string") === Success("foo"))
     assert(config.required[Boolean]("boolean") === Success(true))
     assert(config.required[File]("filename") === Success(newFile))
   }

   private def writeConfig(content: String) = {
     val env = "boo"
     writeFile(content, s"$env.conf")
     new Configured[Nothing] {
       override val environment: String = env
       override def load: Try[Nothing] = ???
     }
   }

  private def writeFile(content: String, filename: String): File = {
    val file = new File(s"target/scala-2.11/classes/$filename")
    Some(new PrintWriter(file)).foreach { p => p.write(content); p.close()}
    file
  }
}