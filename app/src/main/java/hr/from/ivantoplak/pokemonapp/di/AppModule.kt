package hr.from.ivantoplak.pokemonapp.di

import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProviderImpl
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepositoryImpl
import hr.from.ivantoplak.pokemonapp.service.PokemonService
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"

val appModule = module {

    single<PokemonService> {

        androidContext().cacheDir

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    single<PokemonRepository> { PokemonRepositoryImpl(get()) }

    single<CoroutineContextProvider> { CoroutineContextProviderImpl() }

    viewModel { PokemonViewModel(get(), get()) }

}