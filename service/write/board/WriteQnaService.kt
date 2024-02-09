package com.baum.baas.local.service.write.board

import com.baum.baas.global.data.Principal
import com.baum.baas.local.data.board.QnaCreateRequest
import com.baum.baas.local.data.board.QnaUpdateRequest
import com.baum.baas.local.data.board.ReplyCreateRequest
import com.baum.baas.local.data.board.ReplyUpdateRequest
import com.baum.baas.local.repository.read.board.ReadQnaRepository
import com.baum.baas.local.repository.read.board.ReadReplyRepository
import com.baum.baas.local.repository.read.user.ReadUserRepository
import com.baum.baas.local.repository.write.board.WriteQnaRepository
import com.baum.baas.local.repository.write.board.WriteReplyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WriteQnaService(
    private val writeQnaRepository: WriteQnaRepository,
    private val readQnaRepository: ReadQnaRepository,
    private val writeReplyRepository: WriteReplyRepository,
    private val readReplyRepository: ReadReplyRepository,
    private val readUserRepository: ReadUserRepository,
) {

    @Transactional("writeTransactionManager")
    fun createQna(qnaCreateRequest: QnaCreateRequest, principal: Principal): Long {

        return readUserRepository.findByIdOrNull(principal.userId)
            ?.run {
                writeQnaRepository.save(
                    qnaCreateRequest.toEntity(this)
                ).id
            }
            ?: throw IllegalArgumentException("Qna post generation error")
    }

    @Transactional("writeTransactionManager")
    fun updateQna(qnaUpdateRequest: QnaUpdateRequest, principal: Principal): Long {

        return writeQnaRepository.findByIdAndUserId(
            qnaUpdateRequest.id,
            principal.userId
        )
            ?.run {
                qnaUpdateRequest.title?.let { title = it }
                qnaUpdateRequest.content?.let { content = it }
                id
            }
            ?: throw IllegalArgumentException("not found qna")
    }

    @Transactional("writeTransactionManager")
    fun deleteQna(id: Long, principal: Principal) {
        writeQnaRepository.findByIdOrNull(id)
            ?.run {
                if (principal.isSameUser(user.id) || principal.isAdmin()) {
                    writeQnaRepository.findReplyIdByQnaId(id)
                        ?.let {
                            if (it.isNotEmpty()) {
                                writeQnaRepository.deleteReplyById(it)
                            }
                        }
                    writeQnaRepository.deleteById(id)
                }
            }
            ?: throw IllegalArgumentException("not found Qna")
    }

    @Transactional("writeTransactionManager")
    fun createReply(
        id: Long,
        replyCreateRequest: ReplyCreateRequest,
        principal: Principal,
    ): Long {
        return readQnaRepository.findByIdOrNull(id)
            ?.run {
                writeReplyRepository.save(
                    replyCreateRequest.toEntity(
                        qnaId = id,
                        user = readUserRepository.findByIdOrNull(principal.userId)
                            ?: throw UsernameNotFoundException("not found user"),
                    )
                ).id
            }
            ?: throw IllegalStateException("Reply post generation error")
    }

    @Transactional("writeTransactionManager")
    fun updateReply(
        replyUpdateRequest: ReplyUpdateRequest,
        principal: Principal,
    ): Long {
        return writeReplyRepository.findByIdOrNull(replyUpdateRequest.id)
            ?.run {
                if (principal.isSameUser(user.id)) {
                    replyUpdateRequest.title?.let { title = it }
                    replyUpdateRequest.content?.let { content = it }
                    id
                } else {
                    throw IllegalStateException("Mismatched user")
                }
            }
            ?: throw IllegalStateException("not found reply")
    }

    @Transactional("writeTransactionManager")
    fun deleteReply(
        id: Long,
        principal: Principal,
    ) {
        readReplyRepository.findByIdOrNull(id)
            ?.run {
                if (principal.isAdmin() || principal.isSameUser(user.id)) {
                    writeReplyRepository.deleteById(id)
                } else {
                    throw IllegalArgumentException("Mismatched user")
                }
            }
            ?: throw IllegalArgumentException("not found reply")
    }

}