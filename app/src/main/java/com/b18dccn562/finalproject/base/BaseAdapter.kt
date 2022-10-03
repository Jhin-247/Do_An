package com.b18dccn562.finalproject.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Equatable, VB : ViewDataBinding>() :
    RecyclerView.Adapter<BaseHolder<VB>>() {

    private var mData: List<T> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(inflater, parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseHolder<VB>, position: Int) {
        holder.binding.apply {
            bindView(this, mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun submitList(data: List<T>) {
        val mOldList = mData
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemDiffCallback(
                oldList = mOldList,
                newList = data
            )
        )
        mData = data
        diffResult.dispatchUpdatesTo(this)
    }

    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseHolder<VB>
    abstract fun bindView(binding: VB, item: T)

    class ItemDiffCallback<T : Equatable>(
        var oldList: List<T>,
        var newList: List<T>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}