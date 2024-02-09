package com.baum.baas.local.controller.board

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.global.data.Principal
import com.baum.baas.global.document.OpenApiTags
import com.baum.baas.local.data.board.PageResponse
import com.baum.baas.local.data.board.PagingRequest
import com.baum.baas.local.data.board.QnaCreateRequest
import com.baum.baas.local.data.board.QnaDetailResponse
import com.baum.baas.local.data.board.QnaListResponse
import com.baum.baas.local.data.board.QnaUpdateRequest
import com.baum.baas.local.data.board.ReplyCreateRequest
import com.baum.baas.local.data.board.ReplyUpdateRequest
import com.baum.baas.local.data.board.SaveAndUpdateResponse
import com.baum.baas.local.service.read.board.ReadQnaService
import com.baum.baas.local.service.write.board.WriteQnaService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class QnaController(
    private val writeQnaService: WriteQnaService,
    private val readQnaService: ReadQnaService,
) {

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 게시판 목록 조회 예제",
        description = "QnA 목록을 페이징과 정렬을 사용하여 조회합니다."
    )
    @GetMapping("/p/qna")
    fun getQnaList(
        pagingRequest: PagingRequest
    ): PageResponse<QnaListResponse> {
        return readQnaService.getQnaList(pagingRequest).let {
            val qna = it.content
                .map(QnaListResponse::of)
            PageResponse.of(
                qna, it.number, it.totalPages, it.totalElements, pagingRequest.size,
                pagingRequest.sort
            )
        }
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 상세 페이지 조회 예제",
        description = "qna-id를 사용하여 상세 페이지를 조회하며, qna_reply 자식 테이블의 데이터가 같이 리턴됩니다."
    )
    @GetMapping("/qna/{qna-id}")
    fun getQnaDetail(
        @PathVariable("qna-id") id: Long,
    ): QnaDetailResponse {
        return readQnaService.getQna(id).let(QnaDetailResponse::of)
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 게시글 저장 예제",
        description = "게시글 등록 정보를 담은 request 객체를 받아 QnA 게시글을 저장합니다."
    )
    @PostMapping("/qna")
    fun createQna(
        @RequestBody qnaCreateRequest: QnaCreateRequest,
        @AuthenticationPrincipal principal: Principal,
    ): SaveAndUpdateResponse {
        return SaveAndUpdateResponse(
            writeQnaService.createQna(qnaCreateRequest, principal)
        )
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 게시글 수정 예제",
        description = "게시글 수정 정보를 담은 request 객체와 qna-id를 받아 QnA 게시글을 수정합니다."
    )
    @PutMapping("/qna")
    fun updateQna(
        @RequestBody qnaUpdateRequest: QnaUpdateRequest,
        @AuthenticationPrincipal principal: Principal,
    ): SaveAndUpdateResponse {
        return SaveAndUpdateResponse(
            writeQnaService.updateQna(qnaUpdateRequest, principal)
        )
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 게시글 삭제 예제",
        description = "qna-id를 사용하여 QnA 게시글을 삭제합니다."
    )
    @DeleteMapping("/qna/{qna-id}")
    fun deleteQna(
        @PathVariable("qna-id") id: Long,
        @AuthenticationPrincipal principal: Principal,
    ): DefaultResponse {
        writeQnaService.deleteQna(id, principal)

        return DefaultResponse()
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 답변글 저장 예제",
        description = "(ADMIN) qna-id와 답변글 정보를 담은 request 객체를 받아 " +
                "qna-id와 일치하는 데이터가 있는지 확인 후에 데이터가 있다면 답변 글을 저장합니다."
    )
    @PostMapping("/qna/{qna-id}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    fun createReply(
        @RequestBody replyCreateRequest: ReplyCreateRequest,
        @PathVariable("qna-id") id: Long,
        @AuthenticationPrincipal principal: Principal,
    ): SaveAndUpdateResponse {
        return SaveAndUpdateResponse(
            writeQnaService.createReply(id, replyCreateRequest, principal)
        )
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 답변글 수정 예제",
        description = "(ADMIN) 답변 글 정보를 담은 request 객체와 reply-id를 받아 답변 글을 수정합니다."
    )
    @PutMapping("/qna/reply")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateReply(
        @RequestBody replyUpdateRequest: ReplyUpdateRequest,
        @AuthenticationPrincipal principal: Principal,
    ): DefaultResponse {
        return SaveAndUpdateResponse(
            writeQnaService.updateReply(replyUpdateRequest, principal)
        )
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "QnA 게시판 답변",
        description = "(ADMIN) reply-id를 사용하여 답변글을 삭제합니다."
    )
    @DeleteMapping("/qna/reply/{reply-id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteReply(
        @PathVariable("reply-id") id: Long,
        @AuthenticationPrincipal principal: Principal,
    ): DefaultResponse {
        writeQnaService.deleteReply(id, principal)

        return DefaultResponse()
    }

}