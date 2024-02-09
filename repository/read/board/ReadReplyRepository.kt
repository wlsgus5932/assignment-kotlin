package com.baum.baas.local.repository.read.board

import com.baum.baas.local.entity.board.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReadReplyRepository : JpaRepository<Reply, Long>