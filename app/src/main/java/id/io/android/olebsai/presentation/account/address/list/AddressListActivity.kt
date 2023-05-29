package id.io.android.olebsai.presentation.account.address.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityAddressListBinding
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.presentation.account.address.add.AddressAddActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressListActivity : BaseActivity<ActivityAddressListBinding, AddressListViewModel>() {

    override val binding by viewBinding(ActivityAddressListBinding::inflate)
    override val vm by viewModels<AddressListViewModel>()

    private val addressListAdapter by lazy { AddressListAdapter(addressListListener) }

    private var isAddressChanged = false

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddressListActivity::class.java))
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
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.address_list)
        }

        with(binding.rvAddress) {
            adapter = addressListAdapter
            layoutManager = LinearLayoutManager(this@AddressListActivity)
        }
    }

    private fun setupActionView() {
        binding.btnAddAddress.setOnClickListener {
            AddressAddActivity.start(this)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.addressListResult.collectLatest {
                    addressListAdapter.submitList(it)
                    binding.groupEmpty.isVisible = it.isEmpty()
                    binding.rvAddress.isVisible = it.isNotEmpty()

                    if (it.size == 1) {
                        val defaultAddress = it.firstOrNull { address -> address.isDefault }
                        if (defaultAddress == null) {
                            vm.setAddressDefault(it[0])
                        }
                    }
                }
            }
        }

        vm.setAddressDefaultResult.observe(
            onLoading = {},
            onSuccess = {
                isAddressChanged = true
            },
            onError = {
                showInfoDialog(it?.message.orEmpty())
            }
        )
    }

    private val addressListListener = object : AddressListAdapter.Listener {
        override fun onSelectAddress(address: Address) {
            AddressAddActivity.start(this@AddressListActivity, address)
        }

        override fun onSetAddressDefault(address: Address) {
            showSetDefaultAddressDialog(address)
        }
    }

    private fun showSetDefaultAddressDialog(address: Address) {
        Dialog(
            context = this,
            message = getString(R.string.address_set_default_dialog_message),
            positiveButtonText = getString(android.R.string.ok),
            positiveAction = {
                vm.setAddressDefault(address)
            },
            negativeButtonText = getString(R.string.cancel),
        ).show()
    }
}