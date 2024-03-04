package hr.from.ivantoplak.pokemonapp.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import hr.from.ivantoplak.pokemonapp.common.logging.initLogging
import hr.from.ivantoplak.pokemonapp.common.ui.image.createImageLoader

class PokemonApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader = this.createImageLoader()

    override fun onCreate() {
        super.onCreate()

        applicationContext.initDependencyInjection()
        initLogging()
    }
}
