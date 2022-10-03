package com.b18dccn562.finalproject.presentation.screen.login_screen.fragments.sign_in

import android.content.Intent
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.base.BaseFragment
import com.b18dccn562.finalproject.databinding.FragmentSignInBinding
import com.b18dccn562.finalproject.presentation.screen.main_screen.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_sign_in

    override fun initData() {
        Firebase.auth.addAuthStateListener {
            if (Firebase.auth.currentUser != null) {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                mActivityCallback.startActivityFromFragment(intent)
            }
        }
    }

    override fun initYourView() {

    }

    override fun initListener() {
        mBinding.btnSignIn.setOnClickListener {
            if (checkFields()) {
                Firebase.auth.signInWithEmailAndPassword(
                    mBinding.etUsername.text.toString(),
                    mBinding.etPassword.text.toString()
                )
            }
        }
    }

    private fun checkFields(): Boolean {
        if (mBinding.etUsername.text.toString().isEmpty()) {
            return false
        }
        if (mBinding.etPassword.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    override fun initObserver() {

    }
}