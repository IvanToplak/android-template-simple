package hr.from.ivantoplak.pokemonapp.coroutines

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {

    fun main(): CoroutineContext

    fun io(): CoroutineContext

    fun default(): CoroutineContext
}