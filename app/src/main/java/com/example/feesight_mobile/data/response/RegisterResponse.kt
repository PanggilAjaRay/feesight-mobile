package com.example.feesight_mobile.data.response

data class RegisterResponse(
    val id: String,
    val email: String,
    val displayName: String,
    val passwordHash: String
)
