package com.raymanoz.reloaded

trait Logger {
  def error(err: Throwable)
  def error(errMsg: String)
  def info(msg: String)
}

object NoopLogger extends Logger {
  def error(err: Throwable) = {}
  def error(errMsg: String) = {}
  def info(msg: String) = {}
}
