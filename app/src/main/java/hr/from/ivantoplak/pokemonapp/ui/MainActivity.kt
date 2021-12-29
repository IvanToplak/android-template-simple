package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

private const val SPLASH_FADEOUT_DURATION = 300L
private const val SPLASH_ROTATION = 360F

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle the splash screen transition
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.iconView.animate()
                .rotationBy(SPLASH_ROTATION)
                .alpha(0F)
                .setDuration(SPLASH_FADEOUT_DURATION)
                .withEndAction { splashScreenView.remove() }
        }

        setContent {
            PokemonApp()
        }
    }
}
