package com.example.usersearchapplication.utils.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import com.example.usersearchapplication.R
import kotlinx.android.synthetic.main.progress_dialog_view.view.*

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

class CustomProgressDialog {

    var dialog: CustomDialog? = null

    fun show(context: Context): Dialog? {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog_view, null)

        // Progress Bar Color
        setColorFilter(
            view.progress_bar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.colorAccent, null)
        )

        dialog = CustomDialog(context)
        dialog?.setContentView(view)
        dialog?.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.transparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}