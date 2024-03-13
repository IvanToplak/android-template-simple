package hr.from.ivantoplak.pokemonapp.common.api

import hr.from.ivantoplak.pokemonapp.authentication.model.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response

/**
 * AuthInterceptor is responsible for adding the access token header to all HTTP requests.
 */
class AuthInterceptor(private val authRepository: AuthRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .header(
                    "Authorization",
                    "Bearer ${authRepository.getAuthToken()}",
                )
                .build()
        return chain.proceed(request)
    }
}
