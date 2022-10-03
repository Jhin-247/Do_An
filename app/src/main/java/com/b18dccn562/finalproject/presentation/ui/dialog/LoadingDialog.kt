package com.b18dccn562.finalproject.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.b18dccn562.finalproject.databinding.DialogLoadingBinding
import javax.inject.Inject

class LoadingDialog @Inject constructor(
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        return DialogLoadingBinding.inflate(inflater, container, false).root
    }

    fun showDialog(manager: FragmentManager, cancelable: Boolean = true) {
        dialog?.setCancelable(cancelable)
        super.show(manager, tag)
    }

    override fun dismiss() {
        super.dismiss()
        dialog?.setCancelable(true)
    }

}