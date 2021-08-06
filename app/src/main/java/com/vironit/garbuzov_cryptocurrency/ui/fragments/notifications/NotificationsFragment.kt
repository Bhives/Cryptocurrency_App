package com.vironit.garbuzov_cryptocurrency.ui.fragments.notifications

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentNotificationsBinding
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNotificationButton.setOnClickListener {
            val action =
                NotificationsFragmentDirections.actionNotificationsFragmentToAddNotificationFragment()
            findNavController().navigate(action)
        }
    }
}