package id.io.android.olebsai.util.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.io.android.olebsai.databinding.DialogAlertBinding

class Dialog(
    context: Context,
    title: String? = null,
    message: String,
    negativeButtonText: String? = null,
    negativeAction: () -> Unit? = {},
    positiveButtonText: String? = null,
    positiveAction: () -> Unit? = {},
    cancelable: Boolean = true
) : MaterialAlertDialogBuilder(context) {

    private val binding = DialogAlertBinding.inflate(LayoutInflater.from(context))
    private var dialog: AlertDialog

    init {
        dialog = MaterialAlertDialogBuilder(context).create().apply {
            setView(binding.root)
            setCancelable(cancelable)
        }

        binding.title.apply {
            isVisible = title != null
            text = title
        }

        binding.message.text = message

        binding.btnPositive.apply {
            isVisible = positiveButtonText != null
            text = positiveButtonText
            setOnClickListener {
                positiveAction.invoke()
                dialog.dismiss()
            }
        }

        binding.btnNegative.apply {
            isVisible = negativeButtonText != null
            text = negativeButtonText
            setOnClickListener {
                negativeAction.invoke()
                dialog.dismiss()
            }
        }
    }

    override fun show(): AlertDialog {
        dialog.show()
        return dialog
    }
}