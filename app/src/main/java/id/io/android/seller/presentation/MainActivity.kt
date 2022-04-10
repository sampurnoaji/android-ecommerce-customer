package id.io.android.seller.presentation

import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(MainActivity::class.java.simpleName, vm.toString())
    }
}