package hr.from.ivantoplak.pokemonapp.app

import android.content.Context
import hr.from.ivantoplak.pokemonapp.BuildConfig
import hr.from.ivantoplak.pokemonapp.common.commonModule
import hr.from.ivantoplak.pokemonapp.pokemon.pokemonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun Context.initDependencyInjection() {
    startKoin {
        androidContext(this@initDependencyInjection)
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        modules(
            listOf(
                appModule,
                commonModule,
                pokemonModule,
            ),
        )
    }
}
