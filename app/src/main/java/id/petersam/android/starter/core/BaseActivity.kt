package id.petersam.android.starter.core

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import id.petersam.android.starter.util.LoadState


abstract class BaseActivity<B : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    abstract val binding: B
    abstract val vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun <T> LiveData<LoadState<T>>.observe(
        onSuccess: (T?) -> Unit,
        onError: (LoadState.Error?) -> Unit,
        onLoading: () -> Unit = {},
        loadingProgressBar: ProgressBar? = null,
    ) {
        observe(this@BaseActivity) {
            when (it) {
                is LoadState.Loading -> {
                    onLoading()
                    loadingProgressBar?.isVisible = true
                }
                is LoadState.Success -> {
                    onSuccess(it.data)
                    loadingProgressBar?.isVisible = false
                }
                is LoadState.Error -> {
                    onError(it)
                    loadingProgressBar?.isVisible = false
                }
            }
        }
    }
}