package com.baum.baas.local.repository.write.board

import com.baum.baas.local.entity.board.Reply
import org.springframework.data.jpa.repository.JpaRepository


interface WriteReplyRepository : JpaRepository<Reply, Long>