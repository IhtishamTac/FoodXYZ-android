package com.example.foodxyzz.contract.authentication

data class registerRequest(
    val address: String,
    val name: String,
    val password: String,
    val password_confirmation: String,
    val username: String
)