package com.gtavares.whatsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gtavares.whatsapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialiseToolbar()
    }

    private fun initialiseToolbar() {
        val toolbar = binding.includeToolbar.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.register2)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}