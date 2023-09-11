package id.io.android.olebsai.presentation.user.register

import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityRegisterBinding
import id.io.android.olebsai.domain.model.user.ApiAddress
import id.io.android.olebsai.presentation.account.address.add.AddressAddViewModel
import id.io.android.olebsai.util.clearError
import id.io.android.olebsai.util.hideKeyboard
import id.io.android.olebsai.util.isValidEmail
import id.io.android.olebsai.util.setErrorText
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val binding: ActivityRegisterBinding by viewBinding(ActivityRegisterBinding::inflate)
    override val vm: RegisterViewModel by viewModels()
    private val addressViewModel: AddressAddViewModel by viewModels()

    private val provinces = mutableListOf<ApiAddress>()
    private val districts = mutableListOf<ApiAddress>()
    private val subDistricts = mutableListOf<ApiAddress>()

    private var selectedProvince: ApiAddress? = null
    private var selectedDistrict: ApiAddress? = null
    private var selectedSubDistrict: ApiAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupActionView()
        observeViewModel()

        addressViewModel.getProvinces()
    }

    private fun setupView() {

    }

    private fun setupActionView() {
        with(binding) {
            imgBack.setOnClickListener { finish() }

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
            etAddressFull.doOnTextChanged { _, _, _, _ ->
                tilAddressFull.clearError()
            }
            etProvince.doOnTextChanged { _, _, _, _ ->
                tilProvince.clearError()
            }
            etDistrict.doOnTextChanged { _, _, _, _ ->
                tilDistrict.clearError()
            }
            etSubDistrict.doOnTextChanged { _, _, _, _ ->
                tilSubDistrict.clearError()
            }
            etPostalCode.doOnTextChanged { _, _, _, _ ->
                tilPostalCode.clearError()
            }

            with(binding.etProvince) {
                setOnClickListener {
                    hideKeyboard(it)
                    currentFocus?.clearFocus()
                    (it as AutoCompleteTextView).showDropDown()
                }
                setOnItemClickListener { _, _, index, _ ->
                    selectedProvince = provinces[index]
                    selectedProvince?.let { addressViewModel.getDistricts(it.id) }
                }
            }

            with(binding.etDistrict) {
                setOnClickListener {
                    hideKeyboard(it)
                    currentFocus?.clearFocus()
                    (it as AutoCompleteTextView).showDropDown()
                }
                setOnItemClickListener { _, _, index, _ ->
                    selectedDistrict = districts[index]
                    selectedDistrict?.let { addressViewModel.getSubDistricts(it.id) }
                }
            }

            with(binding.etSubDistrict) {
                setOnClickListener {
                    hideKeyboard(it)
                    currentFocus?.clearFocus()
                    (it as AutoCompleteTextView).showDropDown()
                }
                setOnItemClickListener { _, _, index, _ ->
                    selectedSubDistrict = subDistricts[index]
                }
            }

            btnRegister.setOnClickListener {
                hideKeyboard(it)
                currentFocus?.clearFocus()
                doRegister()
            }
        }
    }

    private fun observeViewModel() {
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

        addressViewModel.provincesResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    provinces.clear()
                    provinces.addAll(it)
                    binding.etProvince.setSimpleItems(it.map { address -> address.name }
                        .toTypedArray())
                }
            },
            onError = {
                showInfoDialog(
                    getString(R.string.address_load_failed),
                    onCloseDialog = { finish() },
                )
            },
        )

        addressViewModel.districtsResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    districts.clear()
                    districts.addAll(it)
                    binding.etDistrict.setSimpleItems(it.map { address -> address.name }
                        .toTypedArray())
                }
            },
            onError = {},
        )

        addressViewModel.subDistrictsResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    subDistricts.clear()
                    subDistricts.addAll(it)
                    binding.etSubDistrict.setSimpleItems(it.map { address -> address.name }
                        .toTypedArray())
                }
            },
            onError = {},
        )
    }

    private fun doRegister() {
        with(binding) {
            var isEmptyField = false

            if (vm.name.isEmpty()) {
                tilName.setErrorText()
                isEmptyField = true
            }
            if (vm.phoneNumber.isEmpty()) {
                tilPhoneNumber.setErrorText()
                isEmptyField = true
            }
            if (vm.email.isEmpty()) {
                tilEmail.setErrorText()
                isEmptyField = true
            }
            if (vm.password.isEmpty()) {
                tilPassword.setErrorText()
                isEmptyField = true
            }
            if (vm.repeatPassword.isEmpty()) {
                tilRepeatPassword.setErrorText()
                isEmptyField = true
            }
            val address = binding.etAddressFull.text
            if (address.isNullOrBlank()) {
                tilAddressFull.setErrorText()
                isEmptyField = true
            }
            if (selectedProvince == null) {
                tilProvince.setErrorText()
                isEmptyField = true
            }
            if (selectedDistrict == null) {
                tilDistrict.setErrorText()
                isEmptyField = true
            }
            if (selectedSubDistrict == null) {
                tilSubDistrict.setErrorText()
                isEmptyField = true
            }
            val postalCode = binding.etPostalCode.text
            if (postalCode.isNullOrBlank()) {
                tilPostalCode.setErrorText()
                isEmptyField = true
            }
            if (isEmptyField) return

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
            vm.register(
                address,
                selectedProvince,
                selectedDistrict,
                selectedSubDistrict,
                postalCode
            )
        }
    }
}