package com.so.filem.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.so.filem.R

object LoadingDialog {
    private var dialog: Dialog? = null
    fun startLoading(context: Context) {
        dialog = Dialog(context)
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(R.layout.loading_dialog)
            it.setCancelable(false)
            it.show()
        }
    }

    fun hideLoading() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }
}