package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block.fragment.all_app_fragment

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.databinding.FragmentAllAppBinding
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllAppsFragment : BaseFragment<FragmentAllAppBinding>() {

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
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keySearch = mBinding.etSearch.text.toString()
                mMainViewModel.search(keySearch)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun initObserver() {
        mMainViewModel.listAppToShow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        mMainViewModel.isInitializedData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    loadingDialog?.showDialog(childFragmentManager, false)
                }
                is Resource.Success -> {
                    if (loadingDialog?.isVisible!!) {
                        loadingDialog?.dismiss()
                    }
                }
                null -> {

                }
            }
        }
    }
}