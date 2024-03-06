package hr.from.ivantoplak.pokemonapp.common

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import hr.from.ivantoplak.pokemonapp.common.connectivity.InternetManager
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.db.PokemonDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            PokemonDatabase.Name,
        ).build()
    }

    singleOf(::DispatcherProvider)

    single { get<PokemonDatabase>().pokemonDao() }

    single {
        InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
}
