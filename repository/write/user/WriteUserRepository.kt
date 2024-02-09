package com.baum.baas.local.repository.write.user

import com.baum.baas.local.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface WriteUserRepository : JpaRepository<User, Long>