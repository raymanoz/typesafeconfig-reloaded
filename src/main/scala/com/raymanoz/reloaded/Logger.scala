package com.raymanoz.reloaded

trait Logger {
  def error(err: Throwable)
  def error(errMsg: String)
  def info(msg: String)
}

class DefaultLogger extends Logger {
  def error(err: Throwable) = println(s"Logging an error [$err]")
  def error(errMsg: String) = println(s"Logging an error message [$errMsg]")
  def info(msg: String) = println(s"Logging an info message [$msg]")
}
