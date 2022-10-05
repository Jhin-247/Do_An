package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.blank

import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : BaseFragment<FragmentBlankBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_blank

    override fun initData() {

    }

    override fun initYourView() {

    }

    override fun initListener() {
        mBinding.btnRequestPermission.setOnClickListener {
            mActivityCallback.requestPermission()
        }
    }

    override fun initObserver() {

    }
}