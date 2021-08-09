package com.vironit.garbuzov_cryptocurrency.ui.fragments.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentNotificationsBinding
import com.vironit.garbuzov_cryptocurrency.ui.adapters.NotificationsAdapter
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notifications.*

@AndroidEntryPoint
class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {

    override val viewModel by viewModels<NotificationsViewModel>()
    lateinit var notificationsAdapter: NotificationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNotificationButton.setOnClickListener {
            findNavController().navigate(NotificationsFragmentDirections.actionNotificationsFragmentToAddNotificationFragment())
        }
        setAdapter()
    }

    private fun setAdapter() {
        notificationsAdapter = NotificationsAdapter()
        notificationsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.getAllCustomNotifications().observe(viewLifecycleOwner) {
            notificationsAdapter.submitList(it)
        }
        notificationsRecyclerView.adapter = notificationsAdapter
    }
}