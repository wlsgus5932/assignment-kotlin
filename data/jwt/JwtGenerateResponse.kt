package com.baum.baas.local.data.jwt

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.global.data.JwtToken
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author : kimdonggeol
 * @since : 2023. 11. 24.
 */
data class JwtGenerateResponse(
    @field:Schema(description = "생성한 JWT 토큰 정보")
    val jwtToken: JwtToken,
) : DefaultResponse()
