package id.io.android.olebsai.presentation.user.login

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ActivityLoginBinding
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private val vm by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionView()
        observeViewModel()
    }

    private fun setupActionView() {
        with(binding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            vm.onUsernameChanged(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            vm.onPasswordChanged(text.toString())
        }

        binding.btnLogin.setOnClickListener {
            vm.login()
        }
    }

    private fun observeViewModel() {
        vm.isErrorForm.observe(this) {
            if (it) {
                Dialog(
                    context = this,
                    message = getString(R.string.login_error_form),
                    positiveButtonText = getString(R.string.close),
                ).show()
            }
        }

        vm.login.observe(this) {
            if (it is LoadState.Success) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}