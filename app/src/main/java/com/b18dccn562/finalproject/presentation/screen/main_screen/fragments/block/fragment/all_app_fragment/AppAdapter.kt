package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block.fragment.all_app_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseAdapter
import com.b18dccn562.finalproject.base.BaseHolder
import com.b18dccn562.finalproject.databinding.ItemAppBinding
import com.b18dccn562.finalproject.domain.model.AppInfo
import com.bumptech.glide.Glide
import javax.inject.Inject

class AppAdapter @Inject constructor() : BaseAdapter<AppInfo, ItemAppBinding>() {
    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<ItemAppBinding> {
        return BaseHolder(
            ItemAppBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun bindView(binding: ItemAppBinding, item: AppInfo) {
        Glide.with(binding.ivAppIcon).load(item.mAppIcon).error(R.drawable.ic_default_app_icon)
            .into(binding.ivAppIcon)
        binding.tvIconName.text = item.mAppName
//        binding.tvRecommend.text = binding.tvRecommend.context.getString(R.string.recommend_lock)
        binding.tvRecommend.text = item.mUsedDuration.toString()
    }


}