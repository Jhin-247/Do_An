package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.databinding.FragmentSettingBinding
import com.b18dccn562.finalproject.presentation.screen.login_screen.LogInActivity
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting.adapter.SettingAdapter
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting.adapter.SettingFunctions
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting.adapter.SettingFunctionsImplement
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.setting.adapter.SettingItems
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(), SettingFunctionsImplement {

    private val mSettingViewModel by activityViewModels<SettingViewModel>()

    @Inject
    lateinit var settingAdapter: SettingAdapter

    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initData() {
        createDataAdapter()
        mSettingViewModel.loadAndObserveUser()
    }

    private fun createDataAdapter() {
        val settingFunction = listOf(
            SettingItems(
                SettingFunctions.USER_INFO,
                getString(R.string.account),
                R.drawable.ic_accounts
            ),
            SettingItems(
                SettingFunctions.LOCK_PATTERN,
                getString(R.string.lock_setting),
                R.drawable.ic_lock_setting
            ),
            SettingItems(
                SettingFunctions.LOG_OUT,
                getString(R.string.log_out),
                R.drawable.ic_logout
            )
        )
        settingAdapter.settingFunctionHandle = this
        settingAdapter.submitList(settingFunction)
    }

    override fun initYourView() {
        mBinding.rcvSetting.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.rcvSetting.adapter = settingAdapter
    }

    override fun initListener() {

    }

    override fun initObserver() {
        mSettingViewModel.userImage.observe(this) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Glide.with(this).load(it.data).into(mBinding.ivUserAvatar)
                }
            }
        }
    }

    override fun handleSetting(item: SettingItems) {
        when (item.function) {
            SettingFunctions.USER_INFO -> TODO()
            SettingFunctions.LOCK_PATTERN -> TODO()
            SettingFunctions.LOG_OUT -> {
                val authStateListener = AuthStateListener {
                    if (it.currentUser == null) {
                        val intent = Intent(context, LogInActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        mActivityCallback.startActivityFromFragment(intent)
                    }
                }
                mSettingViewModel.signOut(authStateListener)
            }
        }
    }
}