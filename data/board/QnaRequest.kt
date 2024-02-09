package com.baum.baas.local.data.board

import com.baum.baas.local.entity.board.Qna
import com.baum.baas.local.entity.user.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class QnaCreateRequest(
    @NotBlank
    @Schema(description = "제목")
    val title: String,

    @NotBlank
    @Schema(description = "내용")
    val content: String,
) {
    fun toEntity(user: User): Qna {
        return Qna(
            title = this.title,
            content = this.content,
            user = user,
        )
    }
}

data class QnaUpdateRequest(
    @NotBlank
    @Schema(description = "id")
    val id: Long,

    @Schema(description = "제목")
    val title: String?,

    @Schema(description = "내용")
    val content: String?,
)