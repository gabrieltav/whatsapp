package com.gtavares.whatsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.gtavares.whatsapp.databinding.ActivitySignInBinding
import com.gtavares.whatsapp.utils.displayMessage

class SignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }

    private lateinit var email: String
    private lateinit var password: String

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialiseEventClick()
//        firebaseAuth.signOut()
    }

    override fun onStart() {
        super.onStart()
        verifyLoggedInUser()
    }

    private fun verifyLoggedInUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initialiseEventClick() {
        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener {
            if (validateFields()) {
                signInUser()
            }
        }
    }

    private fun signInUser() {
        firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            displayMessage(getString(R.string.logged_in_successfully))
            startActivity(Intent(this, MainActivity::class.java))
        }.addOnFailureListener { error ->
            try {
                throw error
            } catch (errorFirebaseAuthInvalidUserException: FirebaseAuthInvalidUserException) {
                errorFirebaseAuthInvalidUserException.printStackTrace()
                displayMessage(getString(R.string.error_sign_in_email))
            } catch (errorFirebaseAuthInvalidCredentialsException: FirebaseAuthInvalidCredentialsException) {
                errorFirebaseAuthInvalidCredentialsException.printStackTrace()
                displayMessage(getString(R.string.error_sign_in_auth_invalid_credentials_exception))
            }
        }
    }

    private fun validateFields(): Boolean {
        email = binding.editSignInEmail.text.toString()
        password = binding.editSignInPassword.text.toString()

        if (email.isNotEmpty()) {
            binding.textInputLayoutSignInEmail.error = null
            if (password.isNotEmpty()) {
                binding.textInputLayoutSignInPassword.error = null
                return true
            } else {
                binding.textInputLayoutSignInPassword.error =
                    getString(R.string.validate_error_sign_in_password)
                return false
            }
        } else {
            binding.textInputLayoutSignInEmail.error =
                getString(R.string.validate_error_sign_in_email)
            return false
        }
    }
}