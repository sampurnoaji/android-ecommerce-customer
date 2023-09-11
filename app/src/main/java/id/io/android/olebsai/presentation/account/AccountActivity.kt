package id.io.android.olebsai.presentation.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R.string
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityAccountBinding
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.account.update.UpdateProfileActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class AccountActivity : BaseActivity<ActivityAccountBinding, AccountViewModel>() {

    override val binding: ActivityAccountBinding by viewBinding(ActivityAccountBinding::inflate)
    override val vm: AccountViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AccountActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupActionView()
        observeViewModel()

        vm.getUser()
    }

    private fun setupView() {
        with(binding.toolbar) {
            tvTitle.text = getString(string.account)
            imgBack.setOnClickListener { finish() }
        }
    }

    private fun setupActionView() {
        binding.tvEditProfile.setOnClickListener {
            UpdateProfileActivity.start(this)
        }

        binding.btnLogout.setOnClickListener {
            Dialog(
                context = this,
                message = getString(string.logout_confirmation),
                positiveButtonText = getString(string.logout),
                positiveAction = {
                    mainViewModel.logout()
                    finish()
                },
                negativeButtonText = getString(string.cancel),
            ).show()
        }
    }

    private fun observeViewModel() {
        vm.userResult.observe(
            onSuccess = {
                it?.let { inflateUser(it) }
            },
            onError = {
                vm.getUserCached()?.let { inflateUser(it) }
            }
        )
    }

    private fun inflateUser(user: User) {
        with(binding) {
            tvName.text = user.username
            tvPhone.text = user.phone.ifEmpty { "-" }
            tvEmail.text = user.email

            tvFullAddress.text = user.address.alamat
            tvAddress.text =
                "${user.address.kecamatan}, ${user.address.kota}, ${user.address.provinsi}, ${user.address.kodePos}"
        }
    }
}