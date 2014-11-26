package com.raymanoz.reloaded

import com.raymanoz.reloaded.Read._
import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import scala.util.Try

abstract class Configured[T](logger: Logger = new DefaultLogger) {
  def environment: String

  logger.info(s"Loading config from $environment.conf falling back to reference.conf")

  private lazy val config: Config = Try(FallbackConfigurationLoader.load(environment)).recover { case e: Throwable => shutdownErr(e)}.get

  private def shutdownErr(err: Throwable) = {
    logger.error(err)
    err.printStackTrace()
    exit()
  }

  private def shutdown(err: String) = {
    logger.error(err)
    println(err)
    exit()
  }

  private def exit() = {
    System.exit(-1)
    throw new RuntimeException("Shut up the compiler")
  }

  private def get(key: String): Try[String] = toTried(config.getString(key))

  def optional[A: Read](property: String): Try[Option[A]] = get(property).flatMap(Read[A].read).map(Some(_)).recover { case e: ConfigException.Missing => None}

  def required[A: Read](property: String): Try[A] = {
    get(property) flatMap {
      case value if value.isEmpty => throw new RuntimeException(s"Required property '$property' is empty")
      case value => Read[A].read(value)
    }
  }

  def loadOrDie: T = load.recover { case e => shutdown(e.toString)}.get

  def load: Try[T]

}

object Read {
  def apply[A](implicit r: Read[A]): Read[A] = r

  def toTried[A](a: => A) = Try(a)

  trait Read[A] {
    def read(in: String): Try[A]
  }

  implicit val stringReader = new Read[String] {
    override def read(in: String): Try[String] = scala.util.Success(in)
  }

  implicit val boolReader = new Read[Boolean] {
    override def read(in: String): Try[Boolean] = toTried(in.toBoolean)
  }

  implicit val intReader = new Read[Int] {
    override def read(in: String): Try[Int] = toTried(in.toInt)
  }

  implicit class StringRead(in: String) {
    def read[A: Read]: Try[A] = implicitly[Read[A]].read(in)
  }
}


