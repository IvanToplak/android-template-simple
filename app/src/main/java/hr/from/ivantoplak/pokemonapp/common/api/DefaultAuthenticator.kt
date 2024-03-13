package hr.from.ivantoplak.pokemonapp.common.api

import hr.from.ivantoplak.pokemonapp.authentication.model.AuthRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * DefaultAuthenticator is responsible for refreshing the auth token when it expires.
 */
class DefaultAuthenticator(private val authRepository: AuthRepository) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            // Refresh the token
            authRepository.refreshAuthToken()

            // Return the new request with the updated token
            val newAccessToken = authRepository.getAuthToken()
            response.request
                .newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } catch (e: Exception) {
            // If the token refresh fails, return null so the request fails
            null
        }
    }
}
