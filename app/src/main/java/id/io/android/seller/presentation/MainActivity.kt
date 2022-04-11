package id.io.android.seller.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityMainBinding
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding
        get() = viewBinding(ActivityMainBinding::inflate).value
    override val vm: MainViewModel
        get() = viewModels<MainViewModel>().value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}