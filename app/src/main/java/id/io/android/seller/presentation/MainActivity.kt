package id.io.android.seller.presentation

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.core.BaseActivity
import id.io.android.seller.databinding.ActivityMainBinding
import id.io.android.seller.util.LoadState
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.user.observe(this) {
            when (it) {
                is LoadState.Loading -> {}
                is LoadState.Success -> binding.tv.text = it.data?.id.toString()
                is LoadState.Error -> binding.tv.text = it.message
            }
        }
    }
}