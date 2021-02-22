package hr.from.ivantoplak.pokemonapp.app

import android.app.Application
import hr.from.ivantoplak.pokemonapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PokemonApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupKoin()
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@PokemonApplication)
            androidLogger()
            modules(listOf(appModule))
        }
    }


}