package com.vironit.garbuzov_cryptocurrency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

lateinit var bindingActivity: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityMainBinding.inflate(layoutInflater)
        bindingActivity.root
        val view = bindingActivity.root
        setContentView(view)
        supportFragmentManager.findFragmentById(R.id.currentFragment) as NavHostFragment
    }
}