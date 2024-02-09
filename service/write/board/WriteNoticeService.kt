package com.baum.baas.local.service.write.board

import com.baum.baas.global.data.Principal
import com.baum.baas.local.data.board.NoticeCreateRequest
import com.baum.baas.local.data.board.NoticeUpdateRequest
import com.baum.baas.local.entity.board.Notice
import com.baum.baas.local.repository.read.board.ReadNoticeRepository
import com.baum.baas.local.repository.read.user.ReadUserRepository
import com.baum.baas.local.repository.write.board.WriteNoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WriteNoticeService(
    private val readUserRepository: ReadUserRepository,
    private val writeNoticeRepository: WriteNoticeRepository,
    private val readNoticeRepository: ReadNoticeRepository,
) {
    @Transactional("writeTransactionManager")
    fun createNotice(
        noticeCreateRequest: NoticeCreateRequest,
        principal: Principal,
    ): Long {

        return readUserRepository.findByIdOrNull(principal.userId)
            ?.run {
                writeNoticeRepository.save(
                    noticeCreateRequest.toEntity(this)
                ).id
            }
            ?: throw UsernameNotFoundException("not found admin user")
    }

    @Transactional("writeTransactionManager")
    fun updateNotice(
        noticeUpdateRequest: NoticeUpdateRequest,
        principal: Principal,
    ): Long {
        return writeNoticeRepository.findByIdAndUserId(
            noticeUpdateRequest.id,
            principal.userId,
        )?.run {
            noticeUpdateRequest.title?.let { title = it }
            noticeUpdateRequest.content?.let { content = it }
            id
        }
            ?: throw IllegalArgumentException("not found Notice")
    }

    @Transactional("writeTransactionManager")
    fun deleteNotice(id: Long, principal: Principal) {
        readNoticeRepository.findByIdOrNull(id)
            ?.run {
                writeNoticeRepository.deleteById(id)
            }
            ?: throw IllegalArgumentException("not found Notice")
    }
}
