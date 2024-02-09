package com.baum.baas.local.service.read.board

import com.baum.baas.local.data.board.NoticeDto
import com.baum.baas.local.data.board.PagingRequest
import com.baum.baas.local.repository.read.board.ReadNoticeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReadNoticeService(
    private val readNoticeRepository: ReadNoticeRepository,
) {
    @Transactional("readTransactionManager", readOnly = true)
    fun getNotices(pagingRequest: PagingRequest): Page<NoticeDto> {
        return readNoticeRepository.findAll(pagingRequest.toPageable())
            .map(NoticeDto::of)
    }

    @Transactional("readTransactionManager", readOnly = true)
    fun getNoticeDetail(id: Long): NoticeDto {
        return readNoticeRepository.findByIdOrNull(id)
            ?.let(NoticeDto::of)
            ?: throw IllegalArgumentException("not found notice detail")
    }

}