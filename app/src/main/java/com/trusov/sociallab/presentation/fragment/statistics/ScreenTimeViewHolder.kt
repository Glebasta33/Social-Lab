package com.trusov.sociallab.presentation.fragment.statistics

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.trusov.sociallab.databinding.ScreenTimeRvItemLayoutBinding
import com.trusov.sociallab.domain.entity.AppScreenTime

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