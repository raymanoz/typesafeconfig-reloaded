package com.raymanoz.reloaded

import com.typesafe.config.{ConfigParseOptions, Config}
import com.typesafe.config.ConfigFactory.{defaultOverrides, parseResourcesAnySyntax}
import com.typesafe.config.ConfigResolveOptions.defaults
import com.typesafe.config.impl.ConfigImpl

import scala.util.Try

object FallbackConfigurationLoader {
  def load(environment: String, loader: ClassLoader = getClass.getClassLoader, default: ClassLoader ⇒ Config = defaultOverrides): Config = {
    def loadConfig(resourceBasename: String) = parseResourcesAnySyntax(loader, resourceBasename + ".conf", ConfigParseOptions.defaults().setAllowMissing(false))

    def configs(config: Config): List[Config] = config :: (if (config.hasPath("fallback")) configs(loadConfig(config.getString("fallback"))) else Nil)

    ConfigImpl.reloadSystemPropertiesConfig()

    (default(loader) :: configs(loadConfig(environment))).reduce(_ withFallback _).resolve(defaults())
  }

  def loadOrDie(environment: String, loader: ClassLoader = getClass.getClassLoader, default: ClassLoader ⇒ Config = defaultOverrides): Config =
    Try(FallbackConfigurationLoader.load(environment, loader, default)).recover {
      case e: Throwable =>
        e.printStackTrace()
        sys.exit(-1)
    }.get
}
