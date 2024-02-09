package com.baum.baas.local.data.jwt

import com.baum.baas.global.data.DefaultResponse
import io.jsonwebtoken.Claims
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author : kimdonggeol
 * @since : 2023. 11. 24.
 */
data class JwtAuthResponse(
    @field:Schema(description = "JWT 인증에 사용된 토큰의 claim 정보")
    val claims: Claims,
) : DefaultResponse()
