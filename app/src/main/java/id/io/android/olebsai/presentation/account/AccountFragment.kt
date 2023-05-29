package id.io.android.olebsai.presentation.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentAccountBinding
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.account.address.list.AddressListActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment :
    BaseFragment<FragmentAccountBinding, AccountViewModel>(R.layout.fragment_account) {

    override val binding by viewBinding(FragmentAccountBinding::bind)
    private val actVm by activityViewModels<MainViewModel>()
    override val vm by viewModels<AccountViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resume()
        setupView()
        setupActionView()

        observeViewModel()

        vm.getMasterUser()
        getDefaultAddress()
    }

    fun resume() {
        if (actVm.isLoggedIn.value == false) {
            (requireActivity() as MainActivity).navigateToLogin()
        }
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.isGone = true
            tvTitle.text = getString(R.string.account)
        }
    }

    private fun setupActionView() {
        binding.tvAddressShowAll.setOnClickListener {
            AddressListActivity.start(requireContext())
        }

        binding.btnLogout.setOnClickListener {
            Dialog(
                context = requireContext(),
                message = getString(R.string.logout_confirmation),
                positiveButtonText = getString(R.string.logout),
                positiveAction = {
                    actVm.logout()
                    actVm.checkLoggedInStatus()
                    (requireActivity() as MainActivity).navigateToMenu(R.id.menuHome)
                },
                negativeButtonText = getString(R.string.cancel),
            ).show()
        }
    }

    private fun observeViewModel() {
        vm.masterUserResult.observe(
            onLoading = {},
            onSuccess = {
                it?.let { inflateUser(it) }
            },
            onError = {
                vm.getUser()?.let { inflateUser(it) }
            }
        )
    }

    private fun inflateUser(user: User) {
        binding.tvName.text = user.username
        binding.tvPhone.text = user.phone.ifEmpty { "-" }
        binding.tvEmail.text = user.email
    }

    private fun getDefaultAddress() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getDefaultAddress().let {
                    with(binding) {
                        tvAddressLabel.text = "${it?.label.orEmpty()} - ${it?.name.orEmpty()}"
                        tvAddress.text = it?.address
                        tvAddressNote.text = it?.note
                    }
                }
            }
        }
    }
}