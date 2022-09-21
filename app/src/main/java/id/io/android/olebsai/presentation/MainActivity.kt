package id.io.android.olebsai.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityMainBinding
import id.io.android.olebsai.presentation.account.AccountFragment
import id.io.android.olebsai.presentation.basket.BasketFragment
import id.io.android.olebsai.presentation.category.CategoryFragment
import id.io.android.olebsai.presentation.home.HomeFragment
import id.io.android.olebsai.presentation.order.history.TrxFragment
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment() }
    private val categoryFragment by lazy { CategoryFragment() }
    private val basketFragment by lazy { BasketFragment() }
    private val trxFragment by lazy { TrxFragment() }
    private val accountFragment by lazy { AccountFragment() }
    private var currentFragment: Fragment = homeFragment

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> vm.checkLoggedInStatus()
                Activity.RESULT_CANCELED -> navigateToMenu(R.id.menuHome)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragments()
        observeViewModel()
    }

    private fun setupFragments() {
        showFragment(currentFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    showBottomNav()
                    showFragment(homeFragment)
                    true
                }
                R.id.menuCategory -> {
                    showBottomNav()
                    showFragment(categoryFragment)
                    true
                }
                R.id.menuBasket -> {
                    hideBottomNav()
                    showFragment(basketFragment)
                    if (basketFragment.isAdded) basketFragment.resume()
                    true
                }
                R.id.menuTrx -> {
                    showBottomNav()
                    showFragment(trxFragment)
                    true
                }
                R.id.menuAccount -> {
                    showBottomNav()
                    showFragment(accountFragment)
                    if (accountFragment.isAdded) accountFragment.resume()
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        vm.basketProducts.observe(this) {
            setBasketProductsCountBadge(it.size)
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
        else super.onBackPressed()
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

    private fun hideBottomNav() {
        binding.bottomNavigation.isVisible = false
    }

    private fun showBottomNav() {
        binding.bottomNavigation.isVisible = true
    }

    private fun setBasketProductsCountBadge(count: Int) {
        binding.bottomNavigation.getOrCreateBadge(R.id.menuBasket).number = count
    }
}