package com.baum.baas.local.data.board

import com.baum.baas.local.entity.board.Reply
import java.time.ZonedDateTime


// Dto: service -> controller -------------------
data class ReplyDto(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime?,
) {
    companion object {
        fun of(reply: Reply): ReplyDto {
            return ReplyDto(
                id = reply.id,
                title = reply.title,
                content = reply.content,
                username = reply.user.username,
                regDate = reply.regDate,
                udtDate = reply.udtDate,
            )
        }
    }
}


// Response: controller -> http response ----------------------
data class ReplyResponse(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime?,
) {
    companion object {
        fun of(replyDto: ReplyDto): ReplyResponse {
            return ReplyResponse(
                id = replyDto.id,
                title = replyDto.title,
                content = replyDto.content,
                username = replyDto.username,
                regDate = replyDto.regDate,
                udtDate = replyDto.udtDate,
            )
        }
    }
}