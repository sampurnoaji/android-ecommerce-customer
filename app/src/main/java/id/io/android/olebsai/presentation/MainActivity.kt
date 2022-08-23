package id.io.android.olebsai.presentation

import android.os.Bundle
import androidx.activity.viewModels
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
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override val vm: MainViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment() }
    private val categoryFragment by lazy { CategoryFragment() }
    private val basketFragment by lazy { BasketFragment() }
    private val accountFragment by lazy { AccountFragment() }
    private var currentFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragments()
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
                    showFragment(basketFragment)
                    true
                }
                R.id.menuAccount -> {
                    showFragment(accountFragment)
                    true
                }
                else -> false
            }
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
}