package id.io.android.olebsai.presentation.order.success

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ActivitySuccessBinding
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.CHECKOUT
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.ORDER_FINISH
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.PAYMENT
import id.io.android.olebsai.util.viewBinding

class SuccessActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySuccessBinding::inflate)

    enum class Type {
        CHECKOUT, PAYMENT, ORDER_FINISH
    }

    companion object {
        @JvmStatic
        fun start(context: Context, launcher: ActivityResultLauncher<Intent>, type: Type) {
            launcher.launch(
                Intent(context, SuccessActivity::class.java).apply {
                    putExtra(Type::class.java.simpleName, type.name)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun setupView() {
        with(binding) {
            val type = intent?.extras?.getString(Type::class.java.simpleName).orEmpty()
            when (Type.valueOf(type)) {
                CHECKOUT -> {
                    tvMessage.text = getString(R.string.order_processed)
                    btnAction.apply {
                        text = getString(R.string.order_see)
                        setOnClickListener {
                            MainActivity.start(this@SuccessActivity, R.id.menuOrder)
                            finish()
                        }
                    }
                }
                PAYMENT -> {
                    tvMessage.text = getString(R.string.order_pay_success)
                    btnAction.apply {
                        text = getString(R.string.close)
                        setOnClickListener {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                }
                ORDER_FINISH -> {
                    tvMessage.text = getString(R.string.order_finish_success)
                    btnAction.apply {
                        text = getString(R.string.close)
                        setOnClickListener {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}