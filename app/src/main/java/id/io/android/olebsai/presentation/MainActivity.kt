package id.io.android.olebsai.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityMainBinding
import id.io.android.olebsai.presentation.basket.BasketFragment
import id.io.android.olebsai.presentation.category.CategoryFragment
import id.io.android.olebsai.presentation.home.HomeFragment
import id.io.android.olebsai.presentation.order.history.OrderFragment
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment() }
    private val categoryFragment by lazy { CategoryFragment() }
    private val basketFragment by lazy { BasketFragment() }
    private val trxFragment by lazy { OrderFragment() }
//    private val accountFragment by lazy { AccountFragment() }
    private var currentFragment: Fragment = homeFragment

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> vm.checkLoggedInStatus()
            }
        }

    companion object {
        private const val ARGS_PAGE_ID = "page-id"

        @JvmStatic
        fun start(context: Context, pageId: Int? = null) {
            val starter = Intent(context, MainActivity::class.java).apply {
                putExtra(ARGS_PAGE_ID, pageId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(starter)
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(RequestPermission()) {}

    override fun onStart() {
        super.onStart()
        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.checkLoggedInStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragments()
        checkArguments()
    }

    private fun setupFragments() {
        showFragment(currentFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    showFragment(homeFragment)
                    true
                }

                R.id.menuCategory -> {
                    showFragment(categoryFragment)
                    true
                }

                R.id.menuBasket -> {
                    requireLoggedInAction {
                        showFragment(basketFragment)
                        if (basketFragment.isAdded) basketFragment.resume()
                    }
                }

                R.id.menuTrx -> {
                    showFragment(trxFragment)
                    true
                }

//                R.id.menuAccount -> {
//                    requireLoggedInAction {
//                        showFragment(accountFragment)
//                        if (accountFragment.isAdded) accountFragment.resume()
//                    }
//                }

                else -> false
            }
        }
    }

    private fun checkArguments() {
        intent?.extras?.getInt(ARGS_PAGE_ID)?.let {
            navigateToMenu(it)
        }
    }

    private fun showFragment(fragment: Fragment) {
        with(supportFragmentManager) {
            if (!fragment.isAdded) {
                commit { add(R.id.container, fragment) }
            }
            commit {
                hide(currentFragment)
                show(fragment)
            }
        }
        currentFragment = fragment
    }

    override fun onBackPressed() {
        if (currentFragment != homeFragment) binding.bottomNavigation.selectedItemId = R.id.menuHome
        else finish()
    }

    internal fun navigateToMenu(pageId: Int, args: Bundle? = null) {
        args?.let {
            vm.addBundleToNavigation(args)
        }
        binding.bottomNavigation.selectedItemId = pageId
    }

    internal fun navigateToLogin() {
        launcher.launch(Intent(this, LoginActivity::class.java))
    }

    private fun requireLoggedInAction(action: () -> Unit): Boolean {
        return if (vm.isLoggedIn.value == false) {
            navigateToLogin()
            false
        } else {
            action()
            true
        }
    }
}