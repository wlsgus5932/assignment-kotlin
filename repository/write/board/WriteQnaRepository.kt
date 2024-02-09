package com.baum.baas.local.repository.write.board

import com.baum.baas.local.entity.board.Qna
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface WriteQnaRepository : JpaRepository<Qna, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Qna?

    @Query("SELECT r.id FROM Reply r WHERE r.qnaId = :id")
    fun findReplyIdByQnaId(id: Long): List<Long>?

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.id IN :id")
    fun deleteReplyById(id: List<Long>)

}