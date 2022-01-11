package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPLASH_FADEOUT_DURATION = 300L
private const val SPLASH_ROTATION = 360F

class MainActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if it's not a screen rotation do the splash screen transition
        if (savedInstanceState == null) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.iconView.animate()
                    .rotationBy(SPLASH_ROTATION)
                    .alpha(0F)
                    .setDuration(SPLASH_FADEOUT_DURATION)
                    .withEndAction { splashScreenView.remove() }
            }
        } else {
            setTheme(R.style.Theme_PokemonApp)
        }

        setContent {
            val isReady: Boolean by splashViewModel.isReady
            val showErrorMessage: Boolean by splashViewModel.showErrorMessage

            if (isReady) {
                PokemonApp()
            } else if (showErrorMessage) {
                PokemonAppTheme {
                    ErrorScreen(onClickBack = { onBackPressed() })
                }
            }
        }
    }
}
