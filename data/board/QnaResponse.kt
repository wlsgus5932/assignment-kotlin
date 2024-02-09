package com.baum.baas.local.data.board

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.local.entity.board.Qna
import java.time.ZonedDateTime

// Dto: service -> controller -------------------------
data class QnaDto(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime,
    val reply: List<ReplyDto>?,
) {
    companion object {
        fun of(qna: Qna): QnaDto {
            return QnaDto(
                id = qna.id,
                title = qna.title,
                content = qna.content,
                username = qna.user.username,
                regDate = qna.regDate,
                udtDate = qna.udtDate,
                reply = qna.reply.map {
                    ReplyDto.of(it)
                }
            )
        }
    }
}


// Response: controller -> http ---------------------
data class QnaDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime,
    val reply: List<ReplyResponse>?,
) : DefaultResponse() {
    companion object {
        fun of(qnaDto: QnaDto): QnaDetailResponse {
            return QnaDetailResponse(
                id = qnaDto.id,
                title = qnaDto.title,
                content = qnaDto.content,
                username = qnaDto.username,
                regDate = qnaDto.regDate,
                udtDate = qnaDto.udtDate,
                reply = qnaDto.reply?.map {
                    ReplyResponse.of(it)
                }
            )
        }
    }
}

data class QnaListResponse(
    val id: Long,
    val title: String,
    val username: String,
    val regDate: ZonedDateTime,
    val isReply: Boolean = false,
) {
    companion object {
        fun of(qnaDto: QnaDto): QnaListResponse {
            return QnaListResponse(
                id = qnaDto.id,
                title = qnaDto.title,
                username = qnaDto.username,
                regDate = qnaDto.regDate,
                isReply = qnaDto.reply?.isNotEmpty() ?: false
            )
        }
    }
}


