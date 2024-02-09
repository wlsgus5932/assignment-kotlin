package com.baum.baas.local.data.user

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.local.entity.user.Role
import com.baum.baas.local.entity.user.User
import java.time.ZonedDateTime

/* 지금은 사용안하지만 임시로 삭제하지 않음 */
class UserResponse(
    val id: Long,
    val email: String,
    val password: String,
    val role: MutableSet<Role>,
    val regDate: ZonedDateTime,
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                email = user.username,
                password = user.password,
                role = user.roles,
                regDate = user.regDate
            )
        }
    }
}

class UserResponseList(
    val data: List<User>,
) : DefaultResponse()