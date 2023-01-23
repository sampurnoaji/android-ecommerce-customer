package id.io.android.olebsai.presentation.account.address.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityAddressListBinding
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.presentation.account.address.add.AddressAddActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class AddressListActivity : BaseActivity<ActivityAddressListBinding, AddressListViewModel>() {

    override val binding by viewBinding(ActivityAddressListBinding::inflate)
    override val vm by viewModels<AddressListViewModel>()

    private val addressListAdapter by lazy { AddressListAdapter(addressListListener) }

    private var isAddressChanged = false

    companion object {
        const val RESULT_SELECT_ADDRESS = 1
        const val KEY_SELECTED_ADDRESS = "selected-address"
        private const val KEY_ADDRESS_ID = "address-id"

        fun start(context: Context, launcher: ActivityResultLauncher<Intent>, addressId: Int?) {
            launcher.launch(
                Intent(context, AddressListActivity::class.java).apply {
                    putExtra(KEY_ADDRESS_ID, addressId)
                }
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

        with(binding.rvAddress) {
            adapter = addressListAdapter
            layoutManager = LinearLayoutManager(this@AddressListActivity)
        }

        intent?.getIntExtra(KEY_ADDRESS_ID, 0)?.let {
//            vm.selectAddress(it)
        }
    }

    private fun setupActionView() {
        binding.btnAddAddress.setOnClickListener {
            AddressAddActivity.start(this, launcher)
//            setResult(
//                RESULT_SELECT_ADDRESS,
//                Intent().putExtra(KEY_SELECTED_ADDRESS, vm.selectedAddress)
//            )
//            finish()
        }
    }

    private fun observeViewModel() {
        vm.addressListResult.observe(
            onLoading = {},
            onSuccess = {
                addressListAdapter.submitList(it)
                binding.btnAddAddress.isVisible = true
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )

        vm.setAddressDefaultResult.observe(
            onLoading = {},
            onSuccess = {
                vm.getAddressList()
                isAddressChanged = true
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )
    }

    private val addressListListener = object : AddressListAdapter.Listener {
        override fun onSelectAddress(address: Address) {
            AddressAddActivity.start(this@AddressListActivity, launcher, address)
        }

        override fun onSetAddressDefault(id: Int) {
            showSetDefaultAddressDialog(id)
        }
    }

    private fun showSetDefaultAddressDialog(addressId: Int) {
        Dialog(
            context = this,
            message = getString(R.string.address_set_default_dialog_message),
            positiveButtonText = getString(android.R.string.ok),
            positiveAction = {
                vm.setAddressDefault(addressId)
            },
            negativeButtonText = getString(R.string.cancel),
        ).show()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                vm.getAddressList()
                isAddressChanged = true
            }
        }

    override fun onBackPressed() {
        if (isAddressChanged) {
            setResult(Activity.RESULT_OK)
            finish()
        } else super.onBackPressed()
    }
}