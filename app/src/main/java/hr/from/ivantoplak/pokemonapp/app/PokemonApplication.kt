package hr.from.ivantoplak.pokemonapp.app

import android.app.Application
import hr.from.ivantoplak.pokemonapp.di.initDependencyInjection
import hr.from.ivantoplak.pokemonapp.logging.initLogging

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationContext.initDependencyInjection()
        initLogging()
    }
}
