package com.example.moviePlex.constants

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.example.moviePlex.R

class CommonUtils(private var context: Context) {

    val isNetworkAvailable: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return true
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    return true
                }
            } else {
                return false
            }
            return false
        }

    fun createDialog(icon: Int, title: String, body: String) {
        val customDialog = LayoutInflater.from(context).inflate(R.layout.dialog_item, null)
        val alert: AlertDialog.Builder = AlertDialog.Builder(context)
        alert.setView(customDialog)

        val btnClose: TextView = customDialog.findViewById(R.id.btnClose) as TextView
        val imageView: ImageView = customDialog.findViewById(R.id.imageView) as ImageView
        val titleTv: TextView = customDialog.findViewById(R.id.title) as TextView
        val bodyTv: TextView = customDialog.findViewById(R.id.body) as TextView

        imageView.setImageResource(icon)
        titleTv.text = title
        bodyTv.text = body

        val dialog = alert.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    // soft keyboard hide
    fun hideKeyboard(activity: Activity) {
        // Check if no view has focus:
        try {
            val view = activity.currentFocus
            if (view != null) {
                val inputManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {

        }

    }

    fun createCustomLoader(mContext: Context, isCancelable: Boolean): Dialog {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(isCancelable)
        dialog.setContentView(R.layout.custom_loader)

        //Grab the window of the dialog, and change the width
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        return dialog
    }

    fun showCustomDialog(dialog: Dialog?, context: Context) {
        if (dialog != null) {
            if (!dialog.isShowing)
                if (!(context as Activity).isFinishing) {
                    dialog.show()
                }
        }
    }

    fun dismissCustomDialog(dialog: Dialog?) {
        if (dialog != null) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }
}

