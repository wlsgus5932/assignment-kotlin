package com.baum.baas.local.repository.read.user

import com.baum.baas.local.entity.user.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ReadUserRepository : JpaRepository<User, Long> {
    @EntityGraph(attributePaths = ["roles"])
    fun findByUsername(username: String): User?

}