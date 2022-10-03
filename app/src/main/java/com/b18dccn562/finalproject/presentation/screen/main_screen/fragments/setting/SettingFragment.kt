package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting

import android.content.Intent
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.databinding.FragmentSettingBinding
import com.b18dccn562.finalproject.presentation.screen.login_screen.LogInActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initData() {

    }

    override fun initYourView() {
        mBinding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            Firebase.auth.addAuthStateListener {
                if (it.currentUser == null) {
                    val intent = Intent(context, LogInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    mActivityCallback.startActivityFromFragment(intent)
                }
            }
        }
    }

    override fun initListener() {

    }

    override fun initObserver() {

    }
}