package hr.from.ivantoplak.pokemonapp.di

import androidx.room.Room
import coil.request.ImageRequest
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProviderImpl
import hr.from.ivantoplak.pokemonapp.db.PokemonDatabase
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepositoryImpl
import hr.from.ivantoplak.pokemonapp.service.PokemonService
import hr.from.ivantoplak.pokemonapp.ui.MovesFragment
import hr.from.ivantoplak.pokemonapp.ui.PokemonFragment
import hr.from.ivantoplak.pokemonapp.ui.StatsFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"

typealias ImageRequestBuilderLambda = ImageRequest.Builder.() -> Unit

val appModule = module {

    single<PokemonService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            PokemonDatabase.NAME
        ).build()
    }

    single<DispatcherProvider> { DispatcherProviderImpl() }

    single { get<PokemonDatabase>().pokemonDao() }

    single<PokemonRepository> { PokemonRepositoryImpl(get(), get(), get()) }

    // Fragment scopes
    scope<PokemonFragment> {
        scoped {
            provideImageRequestBuilderLambda()
        }
    }

    scope<MovesFragment> { scoped<Unit> { get() } }

    scope<StatsFragment> { scoped<Unit> { get() } }

    // ViewModels
    viewModel { PokemonViewModel(get(), get()) }

    viewModel { (pokemonId: Int) -> MovesViewModel(pokemonId, get(), get()) }

    viewModel { (pokemonId: Int) -> StatsViewModel(pokemonId, get(), get()) }
}

private fun provideImageRequestBuilderLambda(): ImageRequestBuilderLambda = {
    crossfade(true)
    placeholder(R.drawable.loading_animation)
    error(R.drawable.image_placeholder)
}