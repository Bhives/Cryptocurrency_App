package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentCryptoCurrenciesSearchBinding
import com.vironit.garbuzov_cryptocurrency.ui.adapters.CryptoCurrenciesSearchAdapter
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.CryptoCurrenciesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_currencies_search.*

@AndroidEntryPoint
class CryptoCurrenciesSearchFragment :
    BaseFragment<FragmentCryptoCurrenciesSearchBinding>(R.layout.fragment_crypto_currencies_search) {

    override val viewModel by viewModels<CryptoCurrenciesSearchViewModel>()
    lateinit var cryptoCurrenciesSearchAdapter: CryptoCurrenciesSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_quit -> {
                    Firebase.auth.signOut()
                    googleSignInClient!!.signOut()
                    findNavController().navigate(CryptoCurrenciesSearchFragmentDirections.actionCryptoCurrenciesSearchFragmentToLoginFragment())
                }
            }
            true
        }
        cryptoCurrenciesSearchAdapter = CryptoCurrenciesSearchAdapter()
        setAdapter()
        currenciesListRefresher.setOnRefreshListener {
            setAdapter()
            currenciesListRefresher.isRefreshing = false
        }
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