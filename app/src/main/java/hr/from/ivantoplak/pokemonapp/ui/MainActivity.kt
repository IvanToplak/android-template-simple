package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import hr.from.ivantoplak.pokemonapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch from splash screen theme to main app theme with delay of 400 ms to avoid UI flashing
        if (savedInstanceState == null) {
            Thread.sleep(400)
        }
        setTheme(R.style.Theme_PokemonApp)
        super.onCreate(savedInstanceState)
        setContent {
            PokemonApp()
        }
    }
}
