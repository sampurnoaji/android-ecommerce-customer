package id.io.android.olebsai.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import id.io.android.olebsai.util.LoadState


abstract class BaseFragment<B : ViewBinding, VM : ViewModel>(@LayoutRes id: Int) : Fragment(id) {

    abstract val binding: B
    abstract val vm: VM

    fun <T> LiveData<LoadState<T>>.observe(
        onSuccess: (T?) -> Unit,
        onError: (LoadState.Error?) -> Unit,
        onLoading: () -> Unit = {},
        embedLoading: Boolean = true,
    ) {
        observe(viewLifecycleOwner) {
            when (it) {
                is LoadState.Loading -> {
                    if (embedLoading) (requireActivity() as BaseActivity<*, *>).showLoading()
                    onLoading()
                }
                is LoadState.Success -> {
                    (requireActivity() as BaseActivity<*, *>).hideLoading()
                    onSuccess(it.data)
                }
                is LoadState.Error -> {
                    (requireActivity() as BaseActivity<*, *>).hideLoading()
                    onError(it)
                }
            }
        }
    }
}