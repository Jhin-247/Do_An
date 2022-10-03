package com.b18dccn562.finalproject.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.b18dccn562.finalproject.R
import com.b18dccn562.finalproject.databinding.NotificationTextViewBinding

class NotificationTextView(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val mViewBinding: NotificationTextViewBinding
    private val layoutInflater: LayoutInflater

    private var mErrorMessage: String = ""
    private var mText: String = ""

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mViewBinding = NotificationTextViewBinding.inflate(layoutInflater, this, true)

        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.NotificationTextView,
            0, 0
        ).apply {
            try {
                mErrorMessage = getString(R.styleable.NotificationTextView_ntv_error_text) ?: ""
                mText = getString(R.styleable.NotificationTextView_ntv_text) ?: ""
            } finally {
                recycle()
            }
        }

        mViewBinding.tvNotification.text = mText
    }

    fun getContent(): String {
        return mViewBinding.etContent.text.toString()
    }


}