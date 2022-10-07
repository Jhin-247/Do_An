package com.b18dccn562.finalproject.base

import android.app.AppOpsManager
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.b18dccn562.finalproject.presentation.ui.dialog.LoadingDialog


abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), ActivityCallback {

    protected var mRequestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                onPermissionAccepted()
            }
        }

    protected var loadingDialog: LoadingDialog? = LoadingDialog()

    open fun onPermissionAccepted() {

    }

    protected fun checkPermission(
        context: Context,
        permission: String,
        isScopePermission: Boolean
    ): Boolean {
//        if(isScopePermission && Build.VERSION.SDK_INT > )
        return ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        mBinding = getViewDataBinding()

        initData()
        initYourView()
        setupObserver()
        setupListener()
    }

    override fun onResume() {
        super.onResume()
        observe()
    }

    override fun onPause() {
        super.onPause()
        removeObserve()
    }

    abstract fun getViewDataBinding(): VB
    abstract fun initData()
    abstract fun initYourView()
    abstract fun setupObserver()
    abstract fun setupListener()


    open fun observe() {
    }

    open fun removeObserve() {
    }

    fun hideSupportActionBar() {
        supportActionBar?.let {
            supportActionBar!!.hide()
        }
    }

    fun setStatusBarColor(color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(applicationContext, color)
    }

    @Suppress("DEPRECATION")
    fun checkUsageStatPermission(): Boolean {
        val appOsManager =
            (applicationContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager)
        val mode: Int = if (Build.VERSION.SDK_INT >= 29) {
            appOsManager.unsafeCheckOpNoThrow(
                OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        } else {
            appOsManager.checkOpNoThrow(
                OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun requestUsageStatPermission() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    override fun startActivityFromFragment(intent: Intent) {
        startActivity(intent)
    }

    protected fun showLoadingDialog() {
        if (loadingDialog?.isVisible == true) {
            loadingDialog?.show(supportFragmentManager, "")
        }
    }

    protected fun createSave() {
        loadingDialog = null
    }

    protected fun hideLoadingDialog() {
        if (loadingDialog?.isVisible == true) {
            loadingDialog?.dismiss()
        }
    }
}