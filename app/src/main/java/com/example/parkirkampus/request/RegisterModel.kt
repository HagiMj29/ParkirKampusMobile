package com.example.parkirkampus.request

data class RegisterModel (
    val name : String,
    val email : String,
    val password : String,
    val password_confirmation: String
        )
