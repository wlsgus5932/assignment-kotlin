package com.baum.baas.local.data.user

data class LoginRequest(
    val username: String,
    val password: String,
    val deviceToken: String,
)