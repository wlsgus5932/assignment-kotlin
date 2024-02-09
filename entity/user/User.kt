package com.baum.baas.local.entity.user

import com.baum.baas.local.entity.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val username: String,

    @Column
    val password: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val roles: MutableSet<Role> = mutableSetOf(
        Role(
            role = UserRole.ADMIN
        )
    ),

    @Column(columnDefinition = "TEXT")
    var deviceToken: String? = null,

    @Column(columnDefinition = "TEXT")
    var refreshToken: String? = null,

    ) : BaseEntity()
