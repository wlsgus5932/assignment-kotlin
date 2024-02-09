package com.baum.baas.local.service.read.user

import com.baum.baas.global.data.JwtToken
import com.baum.baas.global.support.JwtManager
import com.baum.baas.local.data.user.LoginRequest
import com.baum.baas.local.entity.user.CustomUserDetails
import com.baum.baas.local.entity.user.User
import com.baum.baas.local.repository.read.user.ReadUserRepository
import com.baum.baas.local.repository.write.user.WriteUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ReadSignService(
    private val readUserRepository: ReadUserRepository,
    private val writeUserRepository: WriteUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtManager: JwtManager,
) : UserDetailsService {

    fun signIn(loginRequest: LoginRequest): JwtToken {
        val user = readUserRepository.findByUsername(loginRequest.username)
            ?: throw UsernameNotFoundException("User info mismatch.")

        val token =
            user.let {
                if (passwordEncoder.matches(loginRequest.password, it.password)) {
                    jwtManager.generateToken(it.id, it.username, it.roles)
                } else
                    throw UsernameNotFoundException("User info mismatch.")
            }

        user.deviceToken = loginRequest.deviceToken
        updateRefreshToken(user, token)

        return token
    }

    fun refreshAccessToken(refreshToken: String): JwtToken {
        val user = readUserRepository.findByUsername(
            jwtManager.getUsernameFromToken(refreshToken)
        )
            ?: throw UsernameNotFoundException("Mismatched refresh token")

        val token = jwtManager.generateToken(user.id, user.username, user.roles)

        updateRefreshToken(user, token)

        return token
    }

    private fun updateRefreshToken(user: User, token: JwtToken) {
        user.refreshToken = token.refreshToken
        writeUserRepository.save(user)
    }


    /*
    * 사용하지않음. 공부용으로 남겨둠.
    * */
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            readUserRepository.findByUsername(username)
                ?.let {
                    CustomUserDetails(it)
                }
                ?: throw IllegalArgumentException("not found user")

        return user
    }


}