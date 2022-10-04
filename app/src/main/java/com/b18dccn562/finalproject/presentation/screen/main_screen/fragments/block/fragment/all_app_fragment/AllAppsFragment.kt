package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block.fragment.all_app_fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.databinding.FragmentAllAppBinding
import com.b18dccn562.finalproject.presentation.screen.main_screen.InitializeState
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainViewModel
import com.b18dccn562.finalproject.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllAppsFragment : BaseFragment<FragmentAllAppBinding>() {

    private val mViewModel by viewModels<AllAppViewModel>()
    private val mMainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var adapter: AppAdapter

    override fun getLayoutId(): Int = R.layout.fragment_all_app

    override fun initData() {
    }

    override fun initYourView() {
        mBinding.rcvItems.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mBinding.rcvItems.adapter = adapter
    }

    override fun initListener() {
    }

    override fun initObserver() {
        mViewModel.getSearchingStatus().observe(this) {

        }
        mMainViewModel.allAppList.observe(this) {
            adapter.submitList(it)
        }
        mMainViewModel.isInitializedData.observe(this) {
            when (it) {
                InitializeState.NOT_INITIALIZED -> {}
                InitializeState.IS_INITIALIZING -> {
                    showLoadingDialog(false)
                }
                InitializeState.INITIALIZED -> {
                    hideLoadingDialog()
                }
                null -> {

                }
            }
        }
    }
}