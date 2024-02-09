package com.baum.baas.local.controller.example

import com.baum.baas.global.data.Principal
import com.baum.baas.local.data.jwt.JwtGenerateResponse
import com.baum.baas.global.document.OpenApiTags
import com.baum.baas.global.support.JwtManager
import com.baum.baas.local.entity.user.Role
import com.baum.baas.local.entity.user.UserRole
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : kimdonggeol
 * @since : 2023. 11. 24.
 */
@RestController
class JwtGenerateController(
    private val jwtManager: JwtManager,
) {

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "JWT 생성 예제",
        description = "JWT 토큰 생성 예제"
    )
    @PatchMapping("/p/example/jwt-generate")
    fun jwtCreate(
        @Valid
        @AuthenticationPrincipal principal: Principal,
    ): JwtGenerateResponse {
        return JwtGenerateResponse(
            jwtManager.generateToken(
                principal.userId,
                principal.username,
                mutableSetOf(Role(role = UserRole.ADMIN))
            )
        )
    }
}
