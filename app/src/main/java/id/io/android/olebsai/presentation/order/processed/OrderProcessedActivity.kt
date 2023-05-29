package id.io.android.olebsai.presentation.order.processed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ActivityOrderProcessedBinding
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.util.viewBinding

class OrderProcessedActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityOrderProcessedBinding::inflate)

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, OrderProcessedActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            MainActivity.start(this, R.id.menuTrx)
            finish()
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}