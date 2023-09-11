package id.io.android.olebsai.presentation.order.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.io.android.olebsai.presentation.order.done.OrderDoneFragment
import id.io.android.olebsai.presentation.order.ongoing.OrderOngoingFragment

class OrderPagerAdapter(fragmentActivity: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentActivity, lifecycle) {

    private val fragments = listOf(
        OrderOngoingFragment(),
        OrderDoneFragment(),
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}