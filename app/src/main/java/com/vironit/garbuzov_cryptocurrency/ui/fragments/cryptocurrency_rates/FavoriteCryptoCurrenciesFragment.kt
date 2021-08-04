package com.vironit.garbuzov_cryptocurrency.ui.fragments.cryptocurrency_rates

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentFavoriteCryptoCurrenciesBinding
import com.vironit.garbuzov_cryptocurrency.ui.adapters.FavoriteCryptoCurrenciesAdapter
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.FavoriteCryptoCurrenciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_crypto_currencies.*

@AndroidEntryPoint
class FavoriteCryptoCurrenciesFragment :
    BaseFragment<FragmentFavoriteCryptoCurrenciesBinding>(R.layout.fragment_favorite_crypto_currencies) {

    override val viewModel by viewModels<FavoriteCryptoCurrenciesViewModel>()
    lateinit var favoriteCryptoCurrenciesAdapter: FavoriteCryptoCurrenciesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        favoriteCryptoCurrenciesAdapter = FavoriteCryptoCurrenciesAdapter(viewModel)
        favoriteCryptoCurrenciesRecyclerView.setHasFixedSize(true)
        favoriteCryptoCurrenciesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.getAllFavoriteCryptoCurrencies().observe(viewLifecycleOwner) {
            favoriteCryptoCurrenciesAdapter.submitList(it)
        }
        favoriteCryptoCurrenciesRecyclerView.adapter = favoriteCryptoCurrenciesAdapter
    }
}