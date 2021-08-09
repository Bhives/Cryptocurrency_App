package com.vironit.garbuzov_cryptocurrency.ui.fragments.cryptocurrency_rates

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentCryptoCurrenciesSearchBinding
import com.vironit.garbuzov_cryptocurrency.ui.adapters.CryptoCurrenciesSearchAdapter
import com.vironit.garbuzov_cryptocurrency.ui.fragments.googleSignInClient
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.cryptocurrency_rates.CryptoCurrenciesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_currencies_search.*
import kotlinx.android.synthetic.main.navigation_header.*

@AndroidEntryPoint
class CryptoCurrenciesSearchFragment :
    BaseFragment<FragmentCryptoCurrenciesSearchBinding>(R.layout.fragment_crypto_currencies_search),
    NavigationView.OnNavigationItemSelectedListener {

    override val viewModel by viewModels<CryptoCurrenciesSearchViewModel>()
    lateinit var cryptoCurrenciesSearchAdapter: CryptoCurrenciesSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoCurrenciesSearchAdapter = CryptoCurrenciesSearchAdapter(viewModel)
        setAdapter()
        currenciesListRefresher.setOnRefreshListener {
            setAdapter()
            currenciesListRefresher.isRefreshing = false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_quit -> {
                Firebase.auth.signOut()
                googleSignInClient!!.signOut()
                findNavController().navigate(CryptoCurrenciesSearchFragmentDirections.actionCryptoCurrenciesSearchFragmentToLoginFragment())
                return true
            }
        }
        return false
    }

    private fun setAdapter() {
        cryptoCurrenciesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.getCryptoCurrencies().observe(viewLifecycleOwner) {
            cryptoCurrenciesSearchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        cryptoCurrenciesRecyclerView.adapter = cryptoCurrenciesSearchAdapter
    }
}