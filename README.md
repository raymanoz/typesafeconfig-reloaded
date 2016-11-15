typesafeconfig-reloaded [![Build Status](https://travis-ci.org/raymanoz/typesafeconfig-reloaded.svg?branch=master)](https://travis-ci.org/raymanoz/typesafeconfig-reloaded)
=======================

A [typesafe config](https://github.com/typesafehub/config) pimp with a easy to use n-deep fallback strategy

Requirements
------------
* [scala](http://www.scala-lang.org) 2.12.x

Usage
-----
Add the following lines to your build.sbt

    resolvers += "Raymond Barlow's repo" at "http://repo.raymanoz.com/"

    libraryDependencies += "com.raymanoz.reloaded" %% "reloaded" % "27"

Start hacking

To load config from the file dev.conf, placed anywhere on the classpath...

    import com.raymanoz.reloaded.FallbackConfigurationLoader
    val config: Config = FallbackConfigurationLoader.load("dev")

A fallback conf file is one which provides defaults for properties e.g.

dev.conf

    fallback=prod
    http.port = 1234

prod.conf

    fallback=reference

reference.conf

    http.port = 8000
    statusUrl = "http://localhost:"${http.port}"/status"

In this case...

    FallbackConfigurationLoader.load("prod").getString("port") // == "8000"
    FallbackConfigurationLoader.load("prod").getString("statusUrl") // == "http://localhost:8000/status"
    FallbackConfigurationLoader.load("dev").getString("port") // == "1234"
    FallbackConfigurationLoader.load("dev").getString("statusUrl") // == "http://localhost:1234/status"

For more examples see

* [FallbackConfigurationLoaderTest.scala](https://github.com/raymanoz/typesafeconfig-reloaded/blob/master/src/test/scala/com/raymanoz/reloaded/FallbackConfigurationLoaderTest.scala)

Code license
------------
Apache License 2.0