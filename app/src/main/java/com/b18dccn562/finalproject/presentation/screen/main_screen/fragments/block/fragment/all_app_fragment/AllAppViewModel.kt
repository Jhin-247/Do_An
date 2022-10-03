package com.b18dccn562.finalproject.presentation.screen.main_screen.fragments.block.fragment.all_app_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllAppViewModel @Inject constructor() : ViewModel() {
    private val isSearching = MutableLiveData(false)


    fun getSearchingStatus(): LiveData<Boolean> = isSearching
}