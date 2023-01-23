package id.io.android.olebsai.core

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.io.android.olebsai.R
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.ui.Dialog


abstract class BaseActivity<B : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    abstract val binding: B
    abstract val vm: VM

    private var loading: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun <T> LiveData<LoadState<T>>.observe(
        onSuccess: (T?) -> Unit,
        onError: (LoadState.Error?) -> Unit,
        onLoading: () -> Unit = {},
        embedLoading: Boolean = true,
    ) {
        observe(this@BaseActivity) {
            when (it) {
                is LoadState.Loading -> {
                    if (embedLoading) showLoading()
                    onLoading()
                }
                is LoadState.Success -> {
                    hideLoading()
                    onSuccess(it.data)
                }
                is LoadState.Error -> {
                    hideLoading()
                    onError(it)
                }
            }
        }
    }

    private fun showLoading() {
        loading = MaterialAlertDialogBuilder(this)
            .setView(R.layout.dialog_loading)
            .setCancelable(false)
            .create()
            .apply {
                this.window?.let { window ->
                    window.setDimAmount(0.75F)
                    window.setBackgroundDrawableResource(android.R.color.transparent)
                    this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
                }
            }
        loading?.show()
    }

    private fun hideLoading() {
        loading?.hide()
    }

    fun showErrorDialog(message: String, onCloseDialog: (() -> Unit)? = null) {
        Dialog(
            context = this,
            message = message,
            positiveButtonText = getString(R.string.close),
            positiveAction = { onCloseDialog?.invoke() }
        ).show()
    }
}