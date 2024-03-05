package com.gtavares.whatsapp.utils

import android.app.Activity
import android.widget.Toast

fun Activity.displayMessage(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}
