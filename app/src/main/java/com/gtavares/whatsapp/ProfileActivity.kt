package com.gtavares.whatsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gtavares.whatsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialiseToolbar()
    }

    private fun initialiseToolbar() {
        val toolbar = binding.includeToolbarProfile.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.edit_profile)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}