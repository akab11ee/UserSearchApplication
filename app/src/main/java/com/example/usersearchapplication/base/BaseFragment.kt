package com.example.usersearchapplication.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding
import com.example.usersearchapplication.R
import com.example.usersearchapplication.databinding.LayoutErrorDialogBinding
import com.example.usersearchapplication.utils.view.CustomProgressDialog

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<*>> : Fragment(),
    LifecycleObserver {

    abstract val viewModel: VM

    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB

    private val progressDialog = CustomProgressDialog()
    private var isInitialized = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateBinding()
        bindUI()
        callApi()
        initReposObserver()
        if (!isInitialized) {
            isInitialized = true
            setUpLoadingDialog()
            setUpErrorDialog()
        }
    }

    protected open fun inflateBinding() {}
    protected open fun bindUI() {}
    protected open fun callApi() {}
    protected open fun initReposObserver() {}

    private fun setUpErrorDialog() {
        viewModel.showErrorDialog.observe(requireActivity()) {
            showErrorDiaogToUser(description = it.errorDescription.message)
        }
    }

    private fun showErrorDiaogToUser(
        title: String = resources.getString(R.string.error_dialog_title),
        description: String,
        isCancelable: Boolean = true,
        isFinish: Boolean = false,
        callback: (() -> Unit)? = null
    ) {

        val dialog = Dialog(requireActivity())
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
            tvError.text = title
            tvErrorDesc.text = description
        }
        dialog.show()
        dialogBinding.btnAction.setOnClickListener {
            dialog.hide()
            callback?.invoke()
            if (isFinish)
                requireActivity().finish()
        }
    }

    private fun setUpLoadingDialog() {
        viewModel.showLoadingDialog.observe(requireActivity()) { loading ->
            if (loading) {
                progressDialog.show(requireActivity())
            } else {
                progressDialog.dialog?.dismiss()
            }
        }
    }
}