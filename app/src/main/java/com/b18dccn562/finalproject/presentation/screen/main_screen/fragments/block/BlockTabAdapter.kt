package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block.fragment.all_app_fragment.AllAppsFragment
import com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.timeline.TimeLineFragment
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class BlockTabAdapter @Inject constructor(
    var fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return AllAppsFragment()
        }
        return TimeLineFragment()
    }

    fun getTitle(position: Int): String {
        if (position == 0) {
            return fragment.getString(R.string.all_apps)
        }
        if (position == 1) {
            return fragment.getString(R.string.limited)
        }
        return fragment.getString(R.string.locked)
    }
}