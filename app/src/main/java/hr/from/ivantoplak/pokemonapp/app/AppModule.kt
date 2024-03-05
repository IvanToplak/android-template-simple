package hr.from.ivantoplak.pokemonapp.app

import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.app.vm.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    singleOf(::AppNavActionProvider)

    // ViewModels
    viewModelOf(::SplashViewModel)
}
