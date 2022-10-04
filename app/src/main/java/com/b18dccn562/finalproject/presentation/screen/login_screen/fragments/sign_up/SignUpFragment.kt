package com.b18dccn562.finalproject.presentation.screen.login_screen.fragments.sign_up

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.common.Resource
import com.b18dccn562.finalproject.databinding.FragmentSignUpBinding
import com.b18dccn562.finalproject.presentation.screen.login_screen.fragments.LoginViewModel
import com.b18dccn562.finalproject.presentation.screen.sign_up_info_screen.SignUpInformationActivity
import com.b18dccn562.finalproject.utils.KeyBoardUtils
import com.b18dccn562.finalproject.utils.UserHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val mSignUpViewModel by activityViewModels<LoginViewModel>()

    @Inject
    lateinit var keyBoardUtils: KeyBoardUtils

    @Inject
    lateinit var userHelper: UserHelper

    override fun getLayoutId(): Int = R.layout.fragment_sign_up

    override fun initData() {

    }

    override fun initYourView() {
    }

    override fun initListener() {
        mBinding.btnSignUp.setOnClickListener {
            val username = mBinding.etUsername.text
            val password = mBinding.etPassword.text
            if (username == null || username.isEmpty()) {
                Toast
                    .makeText(context, getString(R.string.missing_username), Toast.LENGTH_SHORT)
                    .show()
            } else if (password == null || password.isEmpty()) {
                Toast
                    .makeText(context, getString(R.string.missing_password), Toast.LENGTH_SHORT)
                    .show()
            } else {
                mSignUpViewModel.signUp(username.toString(), password.toString())
                keyBoardUtils.hideKeyBoard(mBinding.btnSignUp)
                showLoadingDialog(false)
            }
        }
    }

    override fun initObserver() {
        mSignUpViewModel.loginState.observe(this) { it ->
            when (it) {
                is Resource.Error -> {
                    it.exception?.let {
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    startActivity(Intent(context, SignUpInformationActivity::class.java))
                    activity?.finish()
                }
            }
            hideLoadingDialog()
        }
    }
}