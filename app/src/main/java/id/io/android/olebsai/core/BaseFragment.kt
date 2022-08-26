package id.io.android.olebsai.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<B : ViewBinding, VM : ViewModel>(@LayoutRes id: Int) : Fragment(id) {
    abstract val binding: B
    abstract val vm: VM
}