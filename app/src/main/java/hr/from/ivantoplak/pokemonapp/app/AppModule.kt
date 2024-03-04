package hr.from.ivantoplak.pokemonapp.app

import hr.from.ivantoplak.pokemonapp.app.vm.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    // ViewModels
    viewModelOf(::SplashViewModel)
}
