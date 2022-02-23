package com.trusov.sociallab.feature_statistics.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.trusov.sociallab.databinding.ScreenTimeRvItemLayoutBinding
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime

class ScreenTimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ScreenTimeRvItemLayoutBinding.bind(view)
    fun bind(item: AppScreenTime) {
        with(binding) {
            tvAppName.text = item.appName
            tvScreenTime.text = item.formattedTime()
            ivAppIcon.setImageDrawable(item.icon)
        }
    }
}