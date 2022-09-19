package id.io.android.olebsai.presentation.account.address.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.databinding.ActivityAddressListBinding
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class AddressListActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAddressListBinding::inflate)
    private val vm by viewModels<AddressListViewModel>()

    private val addressListAdapter by lazy {
        AddressListAdapter(object : AddressListAdapter.Listener {
            override fun onSelectAddress(id: Int) {
                vm.selectAddress(id)
            }
        })
    }

    companion object {
        const val RESULT_SELECT_ADDRESS = 1
        const val KEY_SELECTED_ADDRESS = "selected-address"
        private const val KEY_ADDRESS_ID = "address-id"

        fun start(context: Context, launcher: ActivityResultLauncher<Intent>, addressId: Int) {
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
            vm.selectAddress(it)
        }
    }

    private fun setupActionView() {
        binding.btnSelect.setOnClickListener {
            setResult(
                RESULT_SELECT_ADDRESS,
                Intent().putExtra(KEY_SELECTED_ADDRESS, vm.selectedAddress)
            )
            finish()
        }
    }

    private fun observeViewModel() {
        vm.address.observe(this) {
            addressListAdapter.submitList(it)
        }
    }
}