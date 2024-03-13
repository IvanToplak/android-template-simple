package hr.from.ivantoplak.pokemonapp.authentication.model

import hr.from.ivantoplak.pokemonapp.common.localstore.LocalStore
import kotlinx.coroutines.runBlocking

/**
 * Repository for authentication related operations.
 * It uses [LocalStore] to store auth tokens.
 */
interface AuthRepository {
    fun getAuthToken(): String
    fun refreshAuthToken()
}

class AuthRepositoryImpl(
    private val localStore: LocalStore,
) : AuthRepository {
    override fun getAuthToken(): String = runBlocking {
        localStore.getStringValue(key = LocalStore.AuthToken)
    }

    override fun refreshAuthToken() {
        // Refresh the token
    }
}
