package com.b18dccn562.finalproject.presentation.screen.login_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.presentation.screen.login_screen.fragments.sign_in.SignInFragment
import com.b18dccn562.finalproject.presentation.screen.login_screen.fragments.sign_up.SignUpFragment
import javax.inject.Inject

class LoginPagerAdapter @Inject constructor(
    private val fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            SignInFragment()
        } else {
            SignUpFragment()
        }
    }

    fun getTabTitle(position: Int): String {
        return if (position == 0) {
            fragmentActivity.getString(R.string.sign_in)
        } else {
            fragmentActivity.getString(R.string.sign_up)
        }
    }
}