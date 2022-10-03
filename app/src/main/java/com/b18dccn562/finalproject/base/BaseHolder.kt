package com.b18dccn562.finalproject.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseHolder<VB: ViewDataBinding>(var binding: VB) : RecyclerView.ViewHolder(binding.root)