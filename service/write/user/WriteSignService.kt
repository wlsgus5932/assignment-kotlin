package com.baum.baas.local.service.write.user

import com.baum.baas.local.data.user.JoinRequest
import com.baum.baas.local.data.user.JoinRequest.Companion.toEntity
import com.baum.baas.local.repository.read.user.ReadUserRepository
import com.baum.baas.local.repository.write.user.WriteUserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class WriteSignService(
    private val writeUserRepository: WriteUserRepository,
    private val readUserRepository: ReadUserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(joinRequest: JoinRequest): Long {

        return readUserRepository.findByUsername(joinRequest.username)
            ?.run {
                throw UsernameNotFoundException("Duplicate user name")
            }
            ?: run {
                writeUserRepository.save(
                    joinRequest.toEntity(
                        passwordEncoder.encode(joinRequest.password)
                    )
                ).id
            }
    }

}