package com.baum.baas.local.repository.read.board

import com.baum.baas.local.entity.board.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface ReadNoticeRepository : JpaRepository<Notice, Long>