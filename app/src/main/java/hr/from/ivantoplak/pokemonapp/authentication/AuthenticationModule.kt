package hr.from.ivantoplak.pokemonapp.authentication

import hr.from.ivantoplak.pokemonapp.authentication.model.AuthRepository
import hr.from.ivantoplak.pokemonapp.authentication.model.AuthRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationModule = module {

    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}
