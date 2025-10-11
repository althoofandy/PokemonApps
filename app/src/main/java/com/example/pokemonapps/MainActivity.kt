package com.example.pokemonapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.core.utils.Constant.POKENAME_ARGS
import com.example.core.utils.Navigator
import com.example.pokemonapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val navHost by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
    private val navController by lazy {
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun toPokemonDetail(pokeName: String) {
        val bundle = Bundle()
        bundle.putString(POKENAME_ARGS, pokeName)
        navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment, bundle)
    }
}
