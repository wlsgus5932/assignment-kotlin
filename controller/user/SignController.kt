package com.baum.baas.local.controller.user

import com.baum.baas.local.data.jwt.JwtGenerateResponse
import com.baum.baas.local.data.board.SaveAndUpdateResponse
import com.baum.baas.local.data.user.JoinRequest
import com.baum.baas.local.data.user.LoginRequest
import com.baum.baas.local.service.read.user.ReadSignService
import com.baum.baas.local.service.write.user.WriteSignService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/p")
class SignController(
    private val writeSignService: WriteSignService,
    private val readSignService: ReadSignService,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @Valid
        @RequestBody joinRequest: JoinRequest,
    ): SaveAndUpdateResponse {
        return writeSignService.signUp(joinRequest)
            .let(SaveAndUpdateResponse::of)
    }

    @PostMapping("/sign-in")
    fun signIn(
        @Valid
        @RequestBody loginRequest: LoginRequest,
    ): JwtGenerateResponse {
        return JwtGenerateResponse(readSignService.signIn(loginRequest))
    }

    @PostMapping("/refresh-token")
    fun refreshAccessToken(
        @RequestHeader("Authorization") refreshToken: String,
    ): JwtGenerateResponse {
        val validToken = extractRefreshToken(refreshToken)

        return JwtGenerateResponse(
            readSignService.refreshAccessToken(validToken)
        )
    }

    private fun extractRefreshToken(authorizationHeader: String): String {
        val parts = authorizationHeader.split(" ")
        if (parts.size == 2 && parts[0].equals("Bearer", ignoreCase = true)) {
            return parts[1]
        } else {
            throw IllegalArgumentException("Invalid Authorization")
        }
    }
}