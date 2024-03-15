package com.gtavares.whatsapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.gtavares.whatsapp.adapters.ViewPagerAdapter
import com.gtavares.whatsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialiseToolbar()
        initializeBasicNavigation()
    }

    private fun initializeBasicNavigation() {
        val tabLayout = binding.tabLayoutMain
        val viewPager = binding.viewPagerMain
        val flaps = listOf("CHATS", "CONTACTS")

        viewPager.adapter = ViewPagerAdapter(
            flaps, supportFragmentManager, lifecycle
        )

        tabLayout.isTabIndicatorFullWidth = true
        TabLayoutMediator(tabLayout, viewPager) { flap, position ->
            flap.text = flaps[position]
        }.attach()
    }

    private fun initialiseToolbar() {
        val toolbar = binding.includeMainToolbar.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.item_profile -> {
                            startActivity(
                                Intent(applicationContext, ProfileActivity::class.java)
                            )
                        }

                        R.id.item_exit -> {
                            unstickUser()
                        }
                    }
                    return true
                }
            }
        )
    }

    private fun unstickUser() {
        AlertDialog.Builder(this)
            .setTitle("Desligar")
            .setMessage("Deseja realmente sair")
            .setNegativeButton("Não") { dialog, position -> }
            .setPositiveButton("Sim") { dialog, position ->
                firebaseAuth.signOut()
                startActivity(
                    Intent(applicationContext, SignInActivity::class.java)
                )
            }
            .create()
            .show()
    }
}