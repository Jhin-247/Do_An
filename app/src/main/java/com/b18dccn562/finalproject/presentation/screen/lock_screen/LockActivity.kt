package com.b18dccn562.finalproject.presentation.screen.lock_screen

import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseActivity
import com.b18dccn562.finalproject.common.Constants
import com.b18dccn562.finalproject.databinding.ActivityLockBinding
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainActivity
import com.b18dccn562.finalproject.presentation.ui.lock_view.LockView
import com.b18dccn562.finalproject.utils.PatternUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockActivity : BaseActivity<ActivityLockBinding>() {
    private val mViewModel by viewModels<LockViewModel>()
    private var isInitForUnlockApp: Boolean = true

    @Inject
    lateinit var patternUtil: PatternUtil

    override fun getViewDataBinding(): ActivityLockBinding {
        return DataBindingUtil.setContentView(this@LockActivity, R.layout.activity_lock)
    }

    override fun initData() {
        isInitForUnlockApp =
            if (intent != null && intent.extras != null && intent.extras!!.containsKey(Constants.isInitForUnlockApp)) {
                intent.extras!!.getBoolean(Constants.isInitForUnlockApp)
            } else {
                patternUtil.hasPattern()
            }
        mViewModel.setIsUnlockApp(isInitForUnlockApp)
        if (isInitForUnlockApp) {
            mViewModel.setLockStatus(LockPatternStatus.DRAW_TO_OPEN)
        } else {
            mViewModel.setLockStatus(LockPatternStatus.CREATE_PATTERN)
        }
    }

    override fun initYourView() {
        hideSupportActionBar()
    }

    override fun setupObserver() {
        mViewModel.getLockStatus().observe(this) {
            if (it) {
                //Access granted
                startActivity(Intent(this@LockActivity, MainActivity::class.java))
                finish()
            }
        }
        mViewModel.getCreateLockStatus().observe(this) {
            when (it) {
                LockPatternStatus.REDRAW_PATTERN -> {
                    mBinding.lockView.clearPattern()
                    mBinding.tvStatus.text = getString(R.string.redraw_pattern)
                }
                LockPatternStatus.WRONG_PATTERN -> {
                    mBinding.lockView.clearPattern()
                    mBinding.tvStatus.text = getString(R.string.wrong_pattern)
                }
                LockPatternStatus.TOO_SHORT -> {
                    mBinding.lockView.clearPattern()
                    mBinding.tvStatus.text = getString(R.string.short_pattern)
                }
                LockPatternStatus.DRAW_TO_OPEN -> {
                    mBinding.tvStatus.text = getString(R.string.draw_to_open)
                }
                LockPatternStatus.CREATE_PATTERN -> {
                    mBinding.tvStatus.text = getString(R.string.create_pattern)
                }
                null -> {
                    mBinding.lockView.clearPattern()
                }
            }
        }
    }

    override fun setupListener() {
        mBinding.lockView.setOnCompleteListener(object : LockView.OnCompleteDrawingListener {
            override fun onCompleteDrawing(patterns: List<Int>) {
                mViewModel.checkPattern(patterns)
            }

        })
    }

}