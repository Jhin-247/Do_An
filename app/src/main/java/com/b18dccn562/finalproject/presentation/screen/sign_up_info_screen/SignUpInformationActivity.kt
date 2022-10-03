package com.b18dccn562.finalproject.presentation.screen.sign_up_info_screen

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseActivity
import com.b18dccn562.finalproject.databinding.ActivitySignUpInfomationBinding
import com.b18dccn562.finalproject.domain.model.User
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainActivity
import com.b18dccn562.finalproject.utils.UserHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpInformationActivity : BaseActivity<ActivitySignUpInfomationBinding>() {

    private val userInfo = Firebase.auth.currentUser

    private val completeListener = OnCompleteListener<Void> {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @Inject
    lateinit var userHelper: UserHelper

    override fun getViewDataBinding(): ActivitySignUpInfomationBinding =
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_sign_up_infomation
        )

    override fun initData() {

    }

    override fun initYourView() {
        hideSupportActionBar()

    }

    override fun setupObserver() {

    }

    override fun setupListener() {
        mBinding.btnRegister.setOnClickListener {
            if (checkFields()) {
                val gender = if (mBinding.rbMale.isChecked) {
                    "Male"
                } else {
                    "Female"
                }
                userHelper.createUser(
                    User(
                        userInfo!!.uid,
                        mBinding.ntvName.getContent(),
                        "Parent",
                        gender,
                        userInfo.email!!
                    ),
                    completeListener
                )

            }
        }
    }

    private fun checkFields(): Boolean {
        with(mBinding) {
            if (ntvName.getContent().isEmpty()) {
                return false
            }
            if (ntvLastName.getContent().isEmpty()) {
                return false
            }
        }
        return true
    }

}