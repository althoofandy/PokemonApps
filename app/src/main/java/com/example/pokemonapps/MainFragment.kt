package com.example.pokemonapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.core.ui.base.BaseFragment
import com.example.pokemonapps.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val navHost by lazy {
        childFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    private val navController by lazy {
        navHost.findNavController()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.setupWithNavController(navController)
    }

}