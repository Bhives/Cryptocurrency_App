package com.vironit.garbuzov_cryptocurrency.ui.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding>(private val contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    open var binding: V? = null
    open val viewModel by viewModels<ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return binding?.root ?: inflater.inflate(contentLayoutId, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}