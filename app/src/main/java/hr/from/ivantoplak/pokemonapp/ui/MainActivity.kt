package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.ActivityMainBinding
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Pokemon_Main)
        setContentView(binding.root)
        setNavController()
    }

    private fun setNavController() {
        navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
    }
}
