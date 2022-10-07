package com.b18dccn562.finalproject.presentation.screen.sign_up_info_screen

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseActivity
import com.b18dccn562.finalproject.common.Constants
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.databinding.ActivitySignUpInfomationBinding
import com.b18dccn562.finalproject.domain.model.User
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpInformationActivity : BaseActivity<ActivitySignUpInfomationBinding>() {

    private val userInfo = Firebase.auth.currentUser

    private val mViewModel by viewModels<SignUpInfoViewModel>()

    private val mPickImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                val imageUri = data.data!!
                Glide.with(this).load(imageUri).into(mBinding.ivAvatar)
                mViewModel.setImage(imageUri)
            }
        }
    }


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
        mViewModel.uploadImageState.observe(this) {
            when (it) {
                is Resource.Error -> {
                    hideLoadingDialog()
                }
                is Resource.Loading -> {
                    showLoadingDialog()
                }
                is Resource.Success -> {
                    hideLoadingDialog()
                    val imageData = Constants.IMAGE_PATH
                    uploadInformation(imageData)
                }
            }
        }
        mViewModel.saveInformationStatus.observe(this) {
            when (it) {
                is Resource.Error -> {
                    hideLoadingDialog()
                }
                is Resource.Loading -> {
                    showLoadingDialog()
                }
                is Resource.Success -> {
                    hideLoadingDialog()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun setupListener() {
        mBinding.btnRegister.setOnClickListener {
            if (checkFields()) {
                mViewModel.uploadImage()
            }
        }
        mBinding.ivPickImage.setOnClickListener {
            openAndPickImage()
        }
    }

    private fun uploadInformation(imageUrl: String) {
        val gender = if (mBinding.rbMale.isChecked) {
            "Male"
        } else {
            "Female"
        }
        mViewModel.registerInformation(
            User(
                userInfo?.uid!!,
                mBinding.ntvName.getContent(),
                mBinding.spRolePicker.selectedItem.toString(),
                gender,
                userInfo.email!!,
                imageUrl
            )
        )
    }

    private fun openAndPickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        mPickImage.launch(intent)
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