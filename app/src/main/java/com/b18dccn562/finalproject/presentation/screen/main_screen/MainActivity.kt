package com.b18dccn562.finalproject.presentation.screen.main_screen

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.b18dccn562.finalproject.MyApplication
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseActivity
import com.b18dccn562.finalproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mViewModel by viewModels<MainViewModel>()

    private lateinit var mNavHostFragment: NavHostFragment
    private var mNavController: NavController? = null
    private lateinit var myApplication: MyApplication


    override fun getViewDataBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
    }

    override fun initData() {
        myApplication = application as MyApplication
        if (!checkUsageStatPermission()) {
            mViewModel.setHasPermissionStatus(false)
        } else {
            mViewModel.setHasPermissionStatus(true)
        }

        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        mNavHostFragment.let {
            mNavController = mNavHostFragment.navController
        }
    }

    override fun initYourView() {
        hideSupportActionBar()
        setStatusBarColor(R.color.main_color)
    }

    private fun setupNavigation() {
        mNavHostFragment.let {
            NavigationUI.setupWithNavController(mBinding.bottomNavigation, mNavController!!)
        }
        mBinding.bottomNavigation.itemIconTintList = null
        setNavHostFragment(R.navigation.main_nav_graph)
        setBottomMenuItem(R.menu.main_menu)
    }

    private fun setupBlankFragment() {
        setNavHostFragment(R.navigation.no_permission_nav_graph)
        setBottomMenuItem(R.menu.no_permission_menu)
    }

    private fun setBottomMenuItem(menuId: Int) {
        mBinding.bottomNavigation.menu.clear()
        mBinding.bottomNavigation.inflateMenu(menuId)
    }

    private fun setNavHostFragment(navId: Int) {
        val inflater = mNavHostFragment.navController.navInflater
        val graph = inflater.inflate(navId)
        mNavHostFragment.navController.graph = graph
    }

    override fun setupObserver() {
        mViewModel.getHasPermissionStatus().observe(this) {
            if (it) {
                mViewModel.loadData()
                setupNavigation()
            } else {
                setupBlankFragment()
            }
        }
    }


    override fun setupListener() {
    }

    override fun onResume() {
        super.onResume()
        if (mViewModel.isInitializedData.equals(InitializeState.INITIALIZED)) {
            if (checkUsageStatPermission()) {
                mViewModel.setHasPermissionStatus(true)
            }
        }
    }
}