package com.gtavares.whatsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gtavares.whatsapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialiseEventClick()
    }

    private fun initialiseEventClick() {
        binding.textRegister.setOnClickListener {
            startActivity(
                Intent(this, SignUpActivity::class.java)
            )
        }
    }
}