package id.io.android.olebsai.presentation

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityMainBinding
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.user.observe(
            onLoading = {},
            onSuccess = {
                binding.tv.text = it?.id.toString()
            },
            onError = {
                binding.tv.text = it?.message.orEmpty()
            },
        )
    }
}