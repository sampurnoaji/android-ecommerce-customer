package id.petersam.android.starter.presentation

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.petersam.android.starter.core.BaseActivity
import id.petersam.android.starter.databinding.ActivityMainBinding
import id.petersam.android.starter.util.LoadState
import id.petersam.android.starter.util.viewBinding

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