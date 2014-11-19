package com.raymanoz.reloaded

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory.{defaultOverrides, parseResourcesAnySyntax}
import com.typesafe.config.ConfigResolveOptions.defaults
import com.typesafe.config.impl.ConfigImpl

object FallbackConfigurationLoader {
  def load(environment: String, loader: ClassLoader = getClass.getClassLoader): Config = {
    def loadConfig(resourceBasename: String) = parseResourcesAnySyntax(loader, resourceBasename + ".conf")

    def configs(config: Config): List[Config] = config :: (if (config.hasPath("fallback")) configs(loadConfig(config.getString("fallback"))) else Nil)

    ConfigImpl.reloadSystemPropertiesConfig()

    (defaultOverrides(loader) :: configs(loadConfig(environment))).reduce(_ withFallback _).resolve(defaults())
  }
}
