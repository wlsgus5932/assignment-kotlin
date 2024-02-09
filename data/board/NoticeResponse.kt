package com.baum.baas.local.data.board

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.local.entity.board.Notice
import java.time.ZonedDateTime


// DTO: service -> controller -------------------------------
data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime,
) {
    companion object {
        fun of(notice: Notice): NoticeDto {
            return NoticeDto(
                id = notice.id,
                title = notice.title,
                content = notice.content,
                username = notice.user.username,
                regDate = notice.regDate,
                udtDate = notice.udtDate,
            )
        }
    }
}


// Response: controller -> http ----------------------------
data class NoticeDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val regDate: ZonedDateTime,
    val udtDate: ZonedDateTime,
) : DefaultResponse() {
    companion object {
        fun of(noticeDto: NoticeDto): NoticeDetailResponse {
            return NoticeDetailResponse(
                id = noticeDto.id,
                title = noticeDto.title,
                content = noticeDto.content,
                username = noticeDto.username,
                regDate = noticeDto.regDate,
                udtDate = noticeDto.udtDate,
            )
        }
    }
}

data class NoticeListResponse(
    val id: Long,
    val title: String,
    val username: String,
    val regDate: ZonedDateTime,
) {
    companion object {
        fun of(noticeDto: NoticeDto): NoticeListResponse {
            return NoticeListResponse(
                id = noticeDto.id,
                title = noticeDto.title,
                username = noticeDto.username,
                regDate = noticeDto.regDate,
            )
        }
    }
}