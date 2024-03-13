package hr.from.ivantoplak.pokemonapp.common.config

import hr.from.ivantoplak.pokemonapp.BuildConfig

/**
 * Provides configuration values.
 */
class ConfigProvider {
    /**
     * Provides base url for the API.
     */
    fun baseUrl(): String = BuildConfig.API_BASE_URL
}
