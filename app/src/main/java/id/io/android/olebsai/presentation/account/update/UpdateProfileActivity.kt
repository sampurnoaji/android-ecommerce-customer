package id.io.android.olebsai.presentation.account.update

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R.string
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityUpdateProfileBinding
import id.io.android.olebsai.domain.model.user.ApiAddress
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.account.AccountViewModel
import id.io.android.olebsai.presentation.account.address.add.AddressAddViewModel
import id.io.android.olebsai.util.clearError
import id.io.android.olebsai.util.hideKeyboard
import id.io.android.olebsai.util.setErrorText
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class UpdateProfileActivity : BaseActivity<ActivityUpdateProfileBinding, UpdateProfileViewModel>() {

    override val binding by viewBinding(ActivityUpdateProfileBinding::inflate)
    override val vm by viewModels<UpdateProfileViewModel>()

    private val accountViewModel: AccountViewModel by viewModels()
    private val addressViewModel: AddressAddViewModel by viewModels()

    private val provinces = mutableListOf<ApiAddress>()
    private val districts = mutableListOf<ApiAddress>()
    private val subDistricts = mutableListOf<ApiAddress>()

    private var selectedProvince: ApiAddress? = null
    private var selectedDistrict: ApiAddress? = null
    private var selectedSubDistrict: ApiAddress? = null

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, UpdateProfileActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        setupView()
        setupActionView()

        accountViewModel.getUser()
        addressViewModel.getProvinces()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(string.account_edit)
        }
    }

    private fun setupActionView() {
        with(binding) {
            etName.doOnTextChanged { _, _, _, _ ->
                tilName.clearError()
            }
            etPhoneNumber.doOnTextChanged { _, _, _, _ ->
                tilPhoneNumber.clearError()
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

            with(etProvince) {
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

            with(etDistrict) {
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

            with(etSubDistrict) {
                setOnClickListener {
                    hideKeyboard(it)
                    currentFocus?.clearFocus()
                    (it as AutoCompleteTextView).showDropDown()
                }
                setOnItemClickListener { _, _, index, _ ->
                    selectedSubDistrict = subDistricts[index]
                }
            }

            btnUpdate.setOnClickListener {
                hideKeyboard(it)
                currentFocus?.clearFocus()
                if (isValidForm()) {
                    vm.updateProfile(getRegisterParams())
                }
            }
        }
    }

    private fun observeViewModel() {
        accountViewModel.userResult.observe(
            onSuccess = {
                it?.let { inflateUser(it) }
            },
            onError = {
                accountViewModel.getUserCached()?.let { inflateUser(it) }
            }
        )

        vm.updateProfileResult.observe(
            onSuccess = {
                showInfoDialog(
                    getString(string.account_edit_success),
                    onCloseDialog = { finish() },
                )
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.register_failed))
            },
        )

        addressViewModel.provincesResult.observe(
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
                    getString(string.address_load_failed),
                    onCloseDialog = { finish() },
                )
            },
        )

        addressViewModel.districtsResult.observe(
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

    private fun inflateUser(user: User) {
        with(binding) {
            etName.setText(user.username)
            etPhoneNumber.setText(user.phone)
            etEmail.setText(user.email)
            etAddressFull.setText(user.address.alamat)
            etProvince.setText(user.address.provinsi)
            etDistrict.setText(user.address.kota)
            etSubDistrict.setText(user.address.kecamatan)
            etPostalCode.setText(user.address.kodePos)

            selectedProvince =
                ApiAddress(id = user.address.provinsiId, name = user.address.provinsi)
            selectedDistrict = ApiAddress(id = user.address.kotaId, name = user.address.kota)
            selectedSubDistrict =
                ApiAddress(id = user.address.kecamatanId, name = user.address.kecamatan)
        }

        if (user.address.kotaId.isNotEmpty()) addressViewModel.getDistricts(user.address.kotaId)
        if (user.address.kecamatanId.isNotEmpty()) addressViewModel.getSubDistricts(user.address.kecamatanId)
    }

    private fun isValidForm(): Boolean {
        var isEmptyField: Boolean

        with(binding) {
            isEmptyField = isEmptyField(etName, tilName)
            isEmptyField = isEmptyField(etPhoneNumber, tilPhoneNumber)
            isEmptyField = isEmptyField(etAddressFull, tilAddressFull)
            isEmptyField = isEmptyField(etProvince, tilProvince)
            isEmptyField = isEmptyField(etDistrict, tilDistrict)
            isEmptyField = isEmptyField(etSubDistrict, tilSubDistrict)
            isEmptyField = isEmptyField(etPostalCode, tilPostalCode)

            if (isEmptyField) return false

            val phone = etPhoneNumber.text?.trim()
            if (phone?.first() != '0' || phone.length <= 8) {
                tilPhoneNumber.setErrorText(string.form_phone_invalid)
                if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                    scrollView.scrollToDescendant(tilPhoneNumber)
                } else scrollView.scrollTo(tilPhoneNumber.x.toInt(), tilPhoneNumber.y.toInt())
                return false
            }
        }
        return true
    }

    private fun isEmptyField(editText: EditText, inputLayout: TextInputLayout): Boolean {
        if (editText.text.isNullOrBlank()) {
            inputLayout.setErrorText()
            return true
        }
        return false
    }

    private fun getRegisterParams(): RegisterParams {
        with(binding) {
            return RegisterParams(
                name = etName.text?.trim().toString(),
                email = etEmail.text.toString(),
                phoneNumber = etPhoneNumber.text?.trim().toString(),
                password = "",
                address = User.Address(
                    alamat = etAddressFull.text?.trim().toString(),
                    provinsi = selectedProvince?.name.orEmpty(),
                    provinsiId = selectedProvince?.id.orEmpty(),
                    kota = selectedDistrict?.name.orEmpty(),
                    kotaId = selectedDistrict?.id.orEmpty(),
                    kecamatan = selectedSubDistrict?.name.orEmpty(),
                    kecamatanId = selectedSubDistrict?.id.orEmpty(),
                    kodePos = etPostalCode.text?.trim().toString()
                )
            )
        }
    }
}