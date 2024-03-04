package hr.from.ivantoplak.pokemonapp.pokemon

import hr.from.ivantoplak.pokemonapp.pokemon.api.PokemonService
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepositoryImpl
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesViewModel
import hr.from.ivantoplak.pokemonapp.pokemon.vm.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BaseUrl = "https://pokeapi.co/api/v2/"

val pokemonModule = module {

    single<PokemonService> {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    singleOf(::PokemonRepositoryImpl) bind PokemonRepository::class

    // ViewModels
    viewModelOf(::PokemonViewModel)

    viewModelOf(::MovesViewModel)

    viewModelOf(::StatsViewModel)
}
