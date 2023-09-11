package id.io.android.olebsai.presentation.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentAccountBinding
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.account.update.UpdateProfileActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

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

        vm.getUser()
    }

    fun resume() {
        vm.getUserCached()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.isGone = true
            tvTitle.text = getString(R.string.account)
        }
    }

    private fun setupActionView() {
        binding.tvEditProfile.setOnClickListener {
            UpdateProfileActivity.start(requireContext())
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