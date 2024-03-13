package hr.from.ivantoplak.pokemonapp.common

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.squareup.moshi.Moshi
import hr.from.ivantoplak.pokemonapp.BuildConfig
import hr.from.ivantoplak.pokemonapp.common.api.AuthInterceptor
import hr.from.ivantoplak.pokemonapp.common.api.DefaultAuthenticator
import hr.from.ivantoplak.pokemonapp.common.api.OffsetDateTimeAdapter
import hr.from.ivantoplak.pokemonapp.common.config.ConfigProvider
import hr.from.ivantoplak.pokemonapp.common.connectivity.InternetManager
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.db.PokemonDatabase
import hr.from.ivantoplak.pokemonapp.common.localstore.LocalStore
import hr.from.ivantoplak.pokemonapp.common.localstore.LocalStoreImpl
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private const val SocketTimeoutSeconds = 30L

val commonModule = module {

    singleOf(::DispatcherProvider)

    singleOf(::ConfigProvider)

    singleOf(::LocalStoreImpl) bind LocalStore::class

    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            PokemonDatabase.Name,
        ).build()
    }

    single { get<PokemonDatabase>().pokemonDao() }

    single {
        InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    // Logging interceptor
    single<Interceptor>(named("loggingInterceptor")) {
        HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    // Interceptor for adding auth token header
    single<Interceptor>(named("authInterceptor")) {
        AuthInterceptor(authRepository = get())
    }

    // Default authenticator for refreshing auth token
    single<Authenticator>(named("defaultAuthenticator")) {
        DefaultAuthenticator(authRepository = get())
    }

    // HTTP client with logging, auth token header, and auth token refresh logic
    single(named("apiHttpClient")) {
        OkHttpClient.Builder()
            .authenticator(get(named("defaultAuthenticator")) as Authenticator)
            .addInterceptor(get(named("authInterceptor")) as Interceptor)
            .addInterceptor(get(named("loggingInterceptor")) as Interceptor)
            .readTimeout(SocketTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(SocketTimeoutSeconds, TimeUnit.SECONDS)
            .connectTimeout(SocketTimeoutSeconds, TimeUnit.SECONDS)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }

    // Generic moshi json serializer
    single<Moshi>(named("defaultMoshi")) {
        Moshi.Builder()
            .add(OffsetDateTimeAdapter())
            .build()
    }
}
