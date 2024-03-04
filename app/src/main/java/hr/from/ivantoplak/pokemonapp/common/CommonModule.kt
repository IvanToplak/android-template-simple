package hr.from.ivantoplak.pokemonapp.common

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import hr.from.ivantoplak.pokemonapp.common.connectivity.InternetManager
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.db.PokemonDatabase
import hr.from.ivantoplak.pokemonapp.common.vm.ConnectivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
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

    // ViewModels
    viewModelOf(::ConnectivityViewModel)
}
