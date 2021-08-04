package com.vironit.garbuzov_cryptocurrency.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vironit.garbuzov_cryptocurrency.ui.fragments.cryptocurrency_rates.CryptoCurrenciesSearchFragment
import com.vironit.garbuzov_cryptocurrency.ui.fragments.cryptocurrency_rates.FavoriteCryptoCurrenciesFragment

class CryptoCurrencyRatesFragmentPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CryptoCurrenciesSearchFragment()
            }
            1 -> {
                FavoriteCryptoCurrenciesFragment()
            }
            else -> {
                CryptoCurrenciesSearchFragment()
            }
        }
    }
}