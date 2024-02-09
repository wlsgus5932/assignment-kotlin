package com.baum.baas.local.data.user

import com.baum.baas.local.entity.user.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class JoinRequest(
    @field:NotBlank
    @field:Email
    @field:Schema(
        description = "sub(subject) claim 에 들어갈 값",
        example = "test@test.com",
        required = true
    )
    val username: String,

    @field:NotBlank
    @field:Schema(
        description = "비밀번호",
        example = "123",
        required = true
    )
    val password: String,

    ) {
    companion object {
        fun JoinRequest.toEntity(password: String): User {
            return User(
                username = this.username,
                password = password,
            )
        }
    }

}