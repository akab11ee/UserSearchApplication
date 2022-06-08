package com.example.usersearchapplication.base

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.usersearchapplication.R
import com.example.usersearchapplication.databinding.LayoutErrorDialogBinding
import com.example.usersearchapplication.utils.view.CustomProgressDialog
import kotlinx.android.synthetic.main.layout_error_dialog.*


/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> :
    AppCompatActivity() {

    abstract val viewModel: VM
    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB
    private val progressDialog = CustomProgressDialog()

    //Initialize UI and listeners
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        setUpLoadingDialog()
        setupErrorDialog()
        setListener()
    }

    protected open fun setListener() {}

    //Show error dialog in case of any coroutine exception
    private fun setupErrorDialog() {
        viewModel.showErrorDialog.observe(this) {
            showErrorDiaogToUser(description = it.errorDescription.message)
        }
    }

    //Error dialog UI
    private fun showErrorDiaogToUser(
        title: String = resources.getString(R.string.error_dialog_title),
        description: String,
        isCancelable: Boolean = true,
        isFinish: Boolean = false,
        callback: (() -> Unit)? = null
    ) {

        val dialog = Dialog(this)
        val dialogBinding = LayoutErrorDialogBinding.inflate(layoutInflater)
        dialog.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(isCancelable)
            setContentView(dialogBinding.root)
        }
        dialogBinding.apply {
            tv_error.text = title
            tvErrorDesc.text = description
        }
        dialog.show()
        dialogBinding.btnAction.setOnClickListener {
            dialog.hide()
            callback?.invoke()
            if (isFinish)
                finish()
        }
    }

    //show loading dialog when user calls this method
    private fun setUpLoadingDialog() {
        viewModel.showLoadingDialog.observe(this) { loading ->
            if (loading) {
                progressDialog.show(this)
            } else {
                progressDialog.dialog?.dismiss()
            }
        }
    }
}