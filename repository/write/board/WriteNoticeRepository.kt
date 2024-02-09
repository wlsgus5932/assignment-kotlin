package com.baum.baas.local.repository.write.board

import com.baum.baas.local.entity.board.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface WriteNoticeRepository : JpaRepository<Notice, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Notice?

}