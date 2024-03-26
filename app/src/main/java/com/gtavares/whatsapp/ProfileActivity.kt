package com.gtavares.whatsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gtavares.whatsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private var hasCameraPermission = false
    private var hasGalleryPermission = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialiseToolbar()
        requestPermissionsApp()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionsApp() {

        hasCameraPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        hasGalleryPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        val listPermissionsDenied = mutableListOf<String>()

        if (!hasCameraPermission) listPermissionsDenied.add(Manifest.permission.CAMERA)

        if (!hasGalleryPermission) listPermissionsDenied.add(Manifest.permission.READ_MEDIA_IMAGES)

        if (listPermissionsDenied.isNotEmpty()) {
            val managerPermissions = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->

                hasCameraPermission = permissions[Manifest.permission.CAMERA]
                    ?: hasCameraPermission

                hasGalleryPermission = permissions[Manifest.permission.READ_MEDIA_IMAGES]
                    ?: hasGalleryPermission
            }
            managerPermissions.launch(listPermissionsDenied.toTypedArray())
        }
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