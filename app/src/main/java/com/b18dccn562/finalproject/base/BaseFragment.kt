package com.b18dccn562.finalproject.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    lateinit var mBinding: VB
    protected lateinit var mActivityCallback: ActivityCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityCallback = context as BaseActivity<*>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            false
        )
        initData()
        initYourView()
        initListener()
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        initObserver()
    }

    override fun onPause() {
        super.onPause()
        removeObserver()
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initYourView()
    abstract fun initListener()
    abstract fun initObserver()
    fun removeObserver() {}
}