package id.io.android.seller.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseActivity<VM: ViewModel>(private val vmClass: Class<VM>) : AppCompatActivity() {

    open lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(vmClass)
    }
}