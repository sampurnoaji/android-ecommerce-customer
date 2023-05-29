package id.io.android.olebsai.presentation.user.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityLoginBinding
import id.io.android.olebsai.presentation.user.otp.OtpActivity
import id.io.android.olebsai.presentation.user.register.RegisterActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val binding by viewBinding(ActivityLoginBinding::inflate)
    override val vm by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionView()
        observeViewModel()
    }

    private fun setupActionView() {
        binding.imgBack.setOnClickListener { onBackPressed() }

        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            vm.onUsernameChanged(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            vm.onPasswordChanged(text.toString())
        }

        binding.btnLogin.setOnClickListener {
            vm.login()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeViewModel() {
        vm.isEmptyForm.observe(this) {
            if (it) {
                Dialog(
                    context = this,
                    message = getString(R.string.login_error_form_empty),
                    positiveButtonText = getString(R.string.close),
                ).show()
            }
        }

        vm.isErrorForm.observe(this) {
            if (it) {
                Dialog(
                    context = this,
                    message = getString(R.string.login_error_form_invalid),
                    positiveButtonText = getString(R.string.close),
                ).show()
            }
        }

        vm.loginResult.observe(
            onLoading = {},
            onSuccess = {
                it?.first?.let { user ->
                    if (user.otpFlag) {
                        vm.setLoggedIn()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        OtpActivity.start(this, user)
                    }
                }
            },
            onError = {
                showInfoDialog(it?.message.toString())
            }
        )
    }
}