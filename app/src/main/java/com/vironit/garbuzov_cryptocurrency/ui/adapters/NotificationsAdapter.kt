package com.vironit.garbuzov_cryptocurrency.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vironit.garbuzov_cryptocurrency.data.entities.CustomNotification
import com.vironit.garbuzov_cryptocurrency.databinding.NotificationCardBinding

class NotificationsAdapter(
) : ListAdapter<CustomNotification, NotificationsAdapter.NotificationsHolder>(
    NOTIFICATION_COMPARATOR
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationsHolder {
        val binding =
            NotificationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationsHolder(binding)
    }

    override fun onBindViewHolder(holderFavorite: NotificationsHolder, position: Int) {
        holderFavorite.bindNotification(getItem(position))
    }

    inner class NotificationsHolder(private val binding: NotificationCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindNotification(notification: CustomNotification) {
            binding.apply {
                if (notification.notificationName == Firebase.auth.currentUser.toString()) {
                    notificationName.text = notification.notificationName
                }
            }
        }
    }

    companion object {
        private val NOTIFICATION_COMPARATOR = object : DiffUtil.ItemCallback<CustomNotification>() {
            override fun areItemsTheSame(oldItem: CustomNotification, newItem: CustomNotification) =
                oldItem.notificationName == newItem.notificationName

            override fun areContentsTheSame(
                oldItem: CustomNotification,
                newItem: CustomNotification
            ) =
                oldItem == newItem
        }
    }
}