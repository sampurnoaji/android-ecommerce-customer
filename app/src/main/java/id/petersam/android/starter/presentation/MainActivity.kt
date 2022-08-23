package id.petersam.android.starter.presentation

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.petersam.android.starter.core.BaseActivity
import id.petersam.android.starter.databinding.ActivityMainBinding
import id.petersam.android.starter.util.viewBinding

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