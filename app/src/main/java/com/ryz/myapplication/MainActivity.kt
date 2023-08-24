package com.ryz.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ryz.myapplication.viewmodel.ProductViewModel
import com.ryz.myapplication.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModelFactory = ViewModelFactory(application)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
    }
}