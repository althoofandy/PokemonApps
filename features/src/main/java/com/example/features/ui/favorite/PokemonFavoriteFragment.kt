package com.example.features.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features.adapter.FavoritePokemonAdapter
import com.example.features.databinding.FragmentPokemonFavoriteBinding
import com.example.features.viewmodel.FavoritePokemonViewModel

class FavoritePokemonFragment : Fragment() {

    private var _binding: FragmentPokemonFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritePokemonViewModel by viewModels()

    private lateinit var adapter: FavoritePokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritePokemonAdapter()
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
