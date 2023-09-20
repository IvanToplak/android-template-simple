package hr.from.ivantoplak.pokemonapp.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import coil.ImageLoader
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProviderImpl
import hr.from.ivantoplak.pokemonapp.db.PokemonDatabase
import hr.from.ivantoplak.pokemonapp.managers.InternetManager
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepositoryImpl
import hr.from.ivantoplak.pokemonapp.service.PokemonService
import hr.from.ivantoplak.pokemonapp.viewmodel.ConnectivityViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.SplashViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BaseUrl = "https://pokeapi.co/api/v2/"

val appModule = module {

    single<PokemonService> {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            PokemonDatabase.Name,
        ).build()
    }

    singleOf(::DispatcherProviderImpl) bind DispatcherProvider::class

    single { get<PokemonDatabase>().pokemonDao() }

    singleOf(::PokemonRepositoryImpl) bind PokemonRepository::class

    single {
        InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    single {
        ImageLoader.Builder(androidContext())
            .crossfade(true)
            .fallback(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .build()
    }

    // ViewModels
    viewModelOf(::PokemonViewModel)

    viewModelOf(::MovesViewModel)

    viewModelOf(::StatsViewModel)

    viewModelOf(::ConnectivityViewModel)

    viewModelOf(::SplashViewModel)
}
