package com.baum.baas.local.entity.board

import com.baum.baas.local.entity.common.BaseEntity
import com.baum.baas.local.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "qna")
class Qna(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    var title: String,

    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    val reply: MutableList<Reply> = mutableListOf(),

    ) : BaseEntity()