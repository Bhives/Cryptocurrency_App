package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.os.Bundle
import android.view.View
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentCryptoCurrencyRatesBinding
import com.vironit.garbuzov_cryptocurrency.ui.adapters.CryptoCurrencyRatesFragmentPagerAdapter
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import kotlinx.android.synthetic.main.fragment_crypto_currency_rates.*
import kotlin.math.abs

class CryptoCurrencyRatesFragment :
    BaseFragment<FragmentCryptoCurrencyRatesBinding>(R.layout.fragment_crypto_currency_rates) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currencyRatesViewPager = currencyRatesViewPager
        currencyRatesViewPager.adapter =
            CryptoCurrencyRatesFragmentPagerAdapter(childFragmentManager)
        currencyRatesViewPager.currentItem = 0
        currencyRatesViewPager.setPageTransformer(
            false
        ) { v, pos ->
            val opacity = abs(abs(pos) - 1)
            v.alpha = opacity
        }
        currencyRatesTabLayout.setupWithViewPager(currencyRatesViewPager, true)
        currencyRatesTabLayout.getTabAt(0)?.setIcon(R.drawable.ic_all_currencies)
        currencyRatesTabLayout.getTabAt(1)?.setIcon(R.drawable.ic_favorite_currencies)
    }

}