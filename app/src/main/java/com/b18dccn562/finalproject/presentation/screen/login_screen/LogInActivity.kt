package com.b18dccn562.finalproject.presentation.screen.login_screen

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseActivity
import com.b18dccn562.finalproject.databinding.ActivityLogInBinding
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>() {

    @Inject
    lateinit var loginAdapter: LoginPagerAdapter

    override fun getViewDataBinding(): ActivityLogInBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_log_in)
    }

    override fun initData() {
        if (Firebase.auth.currentUser != null ) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun haveInfo(): Unit {

    }

    override fun initYourView() {
        hideSupportActionBar()
        setupTabs()
    }

    private fun setupTabs() {
        mBinding.viewPager.adapter = loginAdapter

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = loginAdapter.getTabTitle(position)
        }.attach()

        mBinding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    mBinding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        mBinding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.tabLayout.selectTab(mBinding.tabLayout.getTabAt(position))
            }
        })
    }

    override fun setupObserver() {
    }

    override fun setupListener() {
    }
}