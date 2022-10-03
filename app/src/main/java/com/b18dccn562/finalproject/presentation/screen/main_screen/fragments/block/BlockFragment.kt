package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block

import androidx.viewpager2.widget.ViewPager2
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.databinding.FragmentBlockBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockFragment : BaseFragment<FragmentBlockBinding>() {

    private lateinit var tabAdapter: BlockTabAdapter

    override fun getLayoutId(): Int = R.layout.fragment_block

    override fun initData() {
        tabAdapter = BlockTabAdapter(this)
    }

    override fun initYourView() {
        setupTabs()
    }

    private fun setupTabs() {
        mBinding.viewPager.adapter = tabAdapter

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = tabAdapter.getTitle(position)
        }.attach()

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.tabLayout.selectTab(mBinding.tabLayout.getTabAt(position))
            }
        })
        mBinding.viewPager.offscreenPageLimit = 3
    }

    override fun initListener() {

    }

    override fun initObserver() {

    }

}