package com.nimbl3.ui.widget

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import com.nimbl3.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_confirmation.view.*


class ConfirmationDialogFragment : DialogFragment() {

    interface ConfirmationDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    lateinit var mListener: ConfirmationDialogListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.dialog_confirmation, container, false)

        view.btnDialogOk.setOnClickListener { mListener.onDialogPositiveClick(this) }
        view.btnDialogCancel.setOnClickListener { mListener.onDialogNegativeClick(this) }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as ConfirmationDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement NoticeDialogListener")
        }
    }

    companion object {
        const val TAG = "CONFIRMATION_DIALOG_FRAGMENT_TAG"
    }
}