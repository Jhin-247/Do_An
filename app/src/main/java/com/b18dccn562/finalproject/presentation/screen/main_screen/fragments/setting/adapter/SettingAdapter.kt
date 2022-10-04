package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.b18dccn562.finalproject.base.BaseAdapter
import com.b18dccn562.finalproject.base.BaseHolder
import com.b18dccn562.finalproject.databinding.ItemSettingBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class SettingAdapter @Inject constructor() : BaseAdapter<SettingItems, ItemSettingBinding>() {

    var settingFunctionHandle: SettingFunctionsImplement? = null


    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<ItemSettingBinding> {
        return BaseHolder(
            ItemSettingBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun bindView(binding: ItemSettingBinding, item: SettingItems) {
        binding.tvFunctionName.text = item.name
        binding.cslFunction.setOnClickListener {
            settingFunctionHandle?.handleSetting(item)
        }
        Glide.with(binding.ivIcon.context).load(item.icon).into(binding.ivIcon)
    }
}