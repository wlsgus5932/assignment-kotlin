package com.baum.baas.local.repository.read.board

import com.baum.baas.local.entity.board.Qna
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ReadQnaRepository : JpaRepository<Qna, Long> {

    @EntityGraph(attributePaths = ["reply"])
    override fun findAll(pageable: Pageable): Page<Qna>

}