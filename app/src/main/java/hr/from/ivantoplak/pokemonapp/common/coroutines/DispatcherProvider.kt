package hr.from.ivantoplak.pokemonapp.common.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatcherProvider {

    fun main(): CoroutineContext = Dispatchers.Main

    fun io(): CoroutineContext = Dispatchers.IO

    fun default(): CoroutineContext = Dispatchers.Default
}
