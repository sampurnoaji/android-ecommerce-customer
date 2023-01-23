package id.io.android.olebsai.presentation.account.address.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityAddressAddBinding
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class AddressAddActivity : BaseActivity<ActivityAddressAddBinding, AddressAddViewModel>() {

    override val binding by viewBinding(ActivityAddressAddBinding::inflate)
    override val vm by viewModels<AddressAddViewModel>()

    private var addressId: Int? = null

    companion object {
        private const val KEY_ADDRESS = "address"

        @JvmStatic
        fun start(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            address: Address? = null
        ) {
            launcher.launch(
                Intent(context, AddressAddActivity::class.java)
                    .putExtra(KEY_ADDRESS, address)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupActionView()
        observeViewModel()
    }

    private fun setupView() {
        with(binding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        intent.getParcelableExtra<Address>(KEY_ADDRESS)?.let {
            addressId = it.id

            binding.etLabel.setText(it.label)
            binding.etRecipient.setText(it.name)
            binding.etRecipientPhone.setText(it.phone)
            binding.etAddressFull.setText(it.address)
            binding.etNote.setText(it.note)

            binding.btnAddAddress.isVisible = false
            binding.btnEdit.isVisible = true
            binding.btnDelete.isVisible = true
        }
    }

    private fun setupActionView() {
        binding.btnAddAddress.setOnClickListener {
            validateInput()
        }

        binding.btnEdit.setOnClickListener {
            validateInput(isAddNewAddress = false)
        }

        binding.btnDelete.setOnClickListener {
            Dialog(
                context = this,
                message = getString(R.string.address_delete_dialog_message),
                positiveButtonText = getString(R.string.delete),
                positiveAction = {
                    addressId?.let { vm.deleteAddress(it) }
                },
                negativeButtonText = getString(R.string.cancel)
            ).show()
        }
    }

    private fun observeViewModel() {
        vm.addAddressResult.observe(
            onLoading = {},
            onSuccess = {
                setResult(Activity.RESULT_OK)
                finish()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )

        vm.updateAddressResult.observe(
            onLoading = {},
            onSuccess = {
                setResult(Activity.RESULT_OK)
                finish()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )

        vm.deleteAddressResult.observe(
            onLoading = {},
            onSuccess = {
                setResult(Activity.RESULT_OK)
                finish()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )
    }

    private fun validateInput(isAddNewAddress: Boolean = false) {
        val label = binding.etLabel.text.toString().trim()
        val name = binding.etRecipient.text.toString().trim()
        val phone = binding.etRecipientPhone.text.toString().trim()
        val address = binding.etAddressFull.text.toString().trim()
        val note = binding.etNote.text.toString().trim()

        if (label.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || note.isEmpty()) {
            Dialog(
                context = this,
                message = getString(R.string.fill_all_field),
                positiveButtonText = getString(android.R.string.ok),
            ).show()
        } else {
            val input = Address(
                id = addressId ?: 0,
                label = label,
                name = name,
                phone = phone,
                address = address,
                note = note,
                isDefault = false,
            )
            if (isAddNewAddress) vm.addAddress(input)
            else vm.updateAddress(input)
        }
    }
}