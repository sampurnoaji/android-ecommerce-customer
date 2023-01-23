package id.io.android.olebsai.presentation.account

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.FragmentAccountBinding
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.account.address.list.AddressListActivity
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding by viewBinding(FragmentAccountBinding::bind)
    private val actVm by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resume()
        setupView()
        setupActionView()
    }

    fun resume() {
        if (actVm.isLoggedIn.value == false) {
            (requireActivity() as MainActivity).navigateToLogin()
        }
    }

    private fun setupView() {
        setHasOptionsMenu(true)
        binding.toolbar.apply {
            inflateMenu(R.menu.menu_account)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menuLogout -> {
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
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupActionView() {
        binding.tvAddressShowAll.setOnClickListener {
            AddressListActivity.start(requireContext(), launcher, null)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }
}