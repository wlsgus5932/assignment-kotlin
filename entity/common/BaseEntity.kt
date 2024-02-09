package com.baum.baas.local.entity.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {

    @Column(name = "reg_date", updatable = false)
    lateinit var regDate: ZonedDateTime

    @Column(name = "udt_date")
    lateinit var udtDate: ZonedDateTime

    @PrePersist
    fun prePersist() {
        regDate = ZonedDateTime.now()
        udtDate = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        udtDate = ZonedDateTime.now()
    }
}