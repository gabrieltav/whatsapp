package com.gtavares.whatsapp.model

data class User(
    var id: String,
    var name: String,
    var email: String,
    var profilePicture: String = ""
)
