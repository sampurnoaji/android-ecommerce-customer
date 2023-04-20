package id.io.android.olebsai.presentation.user.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityRegisterBinding
import id.io.android.olebsai.util.clearError
import id.io.android.olebsai.util.isValidEmail
import id.io.android.olebsai.util.setErrorText
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::inflate)
    override val vm: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupActionView()
        observeRegisterResult()
    }

    private fun setupView() {

    }

    private fun setupActionView() {
        with(binding) {
            imgBack.setOnClickListener { onBackPressed() }

            etEmail.doOnTextChanged { text, _, _, _ ->
                vm.onEmailChanged(text.toString())
                tilEmail.clearError()
            }
            etName.doOnTextChanged { text, _, _, _ ->
                vm.onNameChanged(text.toString())
                tilName.clearError()
            }
            etPhoneNumber.doOnTextChanged { text, _, _, _ ->
                vm.onPhoneNumberChanged(text.toString())
                tilPhoneNumber.clearError()
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                vm.onPasswordChanged(text.toString())
                tilPassword.clearError()
            }
            etRepeatPassword.doOnTextChanged { text, _, _, _ ->
                vm.onRepeatPasswordChanged(text.toString())
                tilRepeatPassword.clearError()
            }

            btnRegister.setOnClickListener {
                doRegister()
            }
        }
    }

    private fun observeRegisterResult() {
        vm.register.observe(
            onLoading = {},
            onSuccess = {
                Dialog(
                    context = this,
                    cancelable = false,
                    message = it ?: getString(R.string.register_success),
                    positiveButtonText = getString(R.string.login),
                    positiveAction = { finish() }
                ).show()
            },
            onError = {
                Dialog(
                    context = this,
                    message = it?.message ?: getString(R.string.register_failed),
                    positiveButtonText = getString(R.string.close)
                ).show()
            },
        )
    }

    private fun doRegister() {
        with(binding) {
            if (vm.name.isEmpty()) {
                tilName.setErrorText()
            }
            if (vm.phoneNumber.isEmpty()) {
                tilPhoneNumber.setErrorText()
            }
            if (vm.email.isEmpty()) {
                tilEmail.setErrorText()
            }
            if (vm.password.isEmpty()) {
                tilPassword.setErrorText()
            }
            if (vm.repeatPassword.isEmpty()) {
                tilRepeatPassword.setErrorText()
                return
            }

            if (vm.phoneNumber.first() != '0' || vm.phoneNumber.length <= 8) {
                tilPhoneNumber.setErrorText(R.string.form_phone_invalid)
                return
            }
            if (!vm.email.isValidEmail()) {
                tilEmail.setErrorText(R.string.form_email_invalid)
                return
            }
            if (vm.password != vm.repeatPassword) {
                tilRepeatPassword.setErrorText(R.string.form_password_not_equal)
                return
            }
            vm.register()
        }
    }
}