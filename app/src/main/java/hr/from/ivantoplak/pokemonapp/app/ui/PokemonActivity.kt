package hr.from.ivantoplak.pokemonapp.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.vm.SplashViewModel
import hr.from.ivantoplak.pokemonapp.common.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SplashFadeoutDuration = 500L
private const val SplashRotation = 360F

class PokemonActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if it's not a screen rotation, show the splash screen
        if (savedInstanceState == null) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.iconView.animate()
                    .rotationBy(SplashRotation)
                    .scaleX(4.0F)
                    .scaleY(4.0F)
                    .alpha(0F)
                    .setDuration(SplashFadeoutDuration)
                    .withEndAction { splashScreenView.remove() }
            }
        } else {
            setTheme(R.style.Theme_PokemonApp)
        }

        setContent {
            val isReady: Boolean by splashViewModel.isReady
            val showErrorMessage: Boolean by splashViewModel.showErrorMessage
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

            if (isReady) {
                PokemonContent(widthSizeClass = widthSizeClass)
            } else if (showErrorMessage) {
                PokemonAppTheme {
                    ErrorScreen(onActionButtonClick = splashViewModel::loadConfig)
                }
            }
        }
    }
}
