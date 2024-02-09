package com.baum.baas.local.data.jwt

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * @author : kimdonggeol
 * @since : 2023. 11. 24.
 */
data class JwtGenerateRequest(

    @field:NotBlank
    @field:Email
    @field:Schema(
        description = "sub(subject) claim 에 들어갈 값",
        example = "dg.kim@bubaum.com",
        required = true
    )
    val username: String,
)
