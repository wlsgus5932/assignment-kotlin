package com.baum.baas.local.data.board

import com.baum.baas.local.entity.board.Reply
import com.baum.baas.local.entity.user.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ReplyCreateRequest(
    @NotBlank
    @Schema(description = "제목")
    val title: String,

    @NotBlank
    @Schema(description = "내용")
    val content: String,
) {
    fun toEntity(user: User, qnaId: Long): Reply {
        return Reply(
            title = this.title,
            content = this.content,
            user = user,
            qnaId = qnaId,
        )
    }
}

data class ReplyUpdateRequest(
    @NotBlank
    @Schema(description = "id")
    val id: Long,

    @Schema(description = "제목")
    val title: String?,

    @Schema(description = "내용")
    val content: String?,
)