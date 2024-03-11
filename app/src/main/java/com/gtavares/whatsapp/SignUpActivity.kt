package com.gtavares.whatsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.gtavares.whatsapp.databinding.ActivitySignUpBinding
import com.gtavares.whatsapp.model.User
import com.gtavares.whatsapp.utils.displayMessage

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialiseToolbar()
        initialiseEventClick()
    }

    private fun validateFields(): Boolean {
        name = binding.editName.text.toString()
        email = binding.editEmail.text.toString()
        password = binding.editPassword.text.toString()
        if (name.isNotEmpty()) {
            binding.textInputSingUpName.error = null
            if (email.isNotEmpty()) {
                binding.textInputSingUpEmail.error = null
                if (password.isNotEmpty()) {
                    binding.textInputSingUpPassword.error = null
                    return true
                } else {
                    binding.textInputSingUpPassword.error =
                        getString(R.string.fill_in_your_password)
                    return false
                }
            } else {
                binding.textInputSingUpEmail.error = getString(R.string.fill_in_your_email)
                return false
            }
        } else {
            binding.textInputSingUpName.error = getString(R.string.fill_in_your_name)
            return false
        }
    }

    private fun initialiseEventClick() {
        binding.btnRegister.setOnClickListener {
            if (validateFields()) {
                registerUser(name, email, password)
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                val userId = result.result.user?.uid
                if (userId != null) {
                    val user = User(
                        userId, name, email
                    )
                    saveUserFirestore(user)
                }
            }
        }.addOnFailureListener { error ->
            try {
                throw error
            } catch (errorFirebaseAuthWeakPasswordException: FirebaseAuthWeakPasswordException) {
                errorFirebaseAuthWeakPasswordException.printStackTrace()
                displayMessage(getString(R.string.invalid_weak_password))
            } catch (errorFirebaseAuthUserCollisionException: FirebaseAuthUserCollisionException) {
                errorFirebaseAuthUserCollisionException.printStackTrace()
                displayMessage(getString(R.string.invalid_email_existing_user))
            } catch (errorFirebaseAuthInvalidCredentialsException: FirebaseAuthInvalidCredentialsException) {
                errorFirebaseAuthInvalidCredentialsException.printStackTrace()
                displayMessage(getString(R.string.invalid_email))
            }
        }
    }

    private fun saveUserFirestore(user: User) {
        firestore
            .collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                displayMessage(getString(R.string.successful_registration))
                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                )
            }.addOnFailureListener {
                displayMessage(getString(R.string.registration_error))
            }
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