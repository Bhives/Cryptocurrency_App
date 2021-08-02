package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentSplashScreenBinding
import com.vironit.garbuzov_cryptocurrency.ui.bindingActivity
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
        }, 2000)
        bindingActivity.bottomNavigationMenu.isVisible = false
    }
}