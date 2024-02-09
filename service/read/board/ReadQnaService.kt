package com.baum.baas.local.service.read.board

import com.baum.baas.local.data.board.PagingRequest
import com.baum.baas.local.data.board.QnaDto
import com.baum.baas.local.repository.read.board.ReadQnaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReadQnaService(
    private val readQnaRepository: ReadQnaRepository,
) {
    @Transactional("readTransactionManager", readOnly = true)
    fun getQnaList(pagingRequest: PagingRequest): Page<QnaDto> {
        return readQnaRepository.findAll(pagingRequest.toPageable()).map(QnaDto::of)
    }

    @Transactional("readTransactionManager", readOnly = true)
    fun getQna(id: Long): QnaDto {
        return QnaDto.of(
            readQnaRepository.findByIdOrNull(id)
                ?: throw IllegalArgumentException("not found Qna Detail")
        )
    }
}
