package com.baum.baas.local.controller.board

import com.baum.baas.global.data.DefaultResponse
import com.baum.baas.global.data.Principal
import com.baum.baas.global.document.OpenApiTags
import com.baum.baas.local.data.board.NoticeCreateRequest
import com.baum.baas.local.data.board.NoticeDetailResponse
import com.baum.baas.local.data.board.NoticeListResponse
import com.baum.baas.local.data.board.NoticeUpdateRequest
import com.baum.baas.local.data.board.PageResponse
import com.baum.baas.local.data.board.PagingRequest
import com.baum.baas.local.data.board.SaveAndUpdateResponse
import com.baum.baas.local.service.read.board.ReadNoticeService
import com.baum.baas.local.service.write.board.WriteNoticeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NoticeController(
    private val writeNoticeService: WriteNoticeService,
    private val readNoticeService: ReadNoticeService,
) {
    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "공지사항 목록 조회 예제",
        description = "공지사항 목록을 페이징과 정렬을 사용하여 조회합니다."
    )
    @GetMapping("/p/notice")
    fun getNotices(
        pagingRequest: PagingRequest
    ): PageResponse<NoticeListResponse> {
        return readNoticeService.getNotices(pagingRequest)
            .let {
                val content = it.content
                    .map(NoticeListResponse::of)
                PageResponse.of(
                    content,
                    it.number,
                    it.totalPages,
                    it.totalElements,
                    pagingRequest.size,
                    pagingRequest.sort
                )
            }
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "공지사항 상세 페이지 조회 예제",
        description = "notice-id를 받아 공지사항 상세 페이지를 조회합니다."
    )
    @GetMapping("/p/notice/{notice-id}")
    fun getNoticeDetail(
        @PathVariable("notice-id") id: Long,
    ): NoticeDetailResponse {
        return readNoticeService.getNoticeDetail(id)
            .let(NoticeDetailResponse::of)
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "공지사항 게시글 등록 예제",
        description = "(ADMIN) 공지사항 등록 정보를 담은 request 객체를 받아 공지사항 게시글을 저장합니다."
    )
    @PostMapping("/notice")
    @PreAuthorize("hasRole('ADMIN')")
    fun createNotice(
        @RequestBody noticeCreateRequest: NoticeCreateRequest,
        @AuthenticationPrincipal principal: Principal,
        //TODO: @AuthenticationPrincipal 미사용시 valid 에러 확인필요
    ): SaveAndUpdateResponse {
        return writeNoticeService.createNotice(
            noticeCreateRequest,
            principal,
        ).let(SaveAndUpdateResponse::of)
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "공지사항 게시글 수정 예제",
        description = "(ADMIN) 공지사항 수정 정보를 담은 request 객체를 받아 공지사항 게시글을 수정합니다."
    )
    @PutMapping("/notice")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateNotice(
        @RequestBody noticeUpdateRequest: NoticeUpdateRequest,
        @AuthenticationPrincipal principal: Principal,
    ): SaveAndUpdateResponse {
        return writeNoticeService.updateNotice(
            noticeUpdateRequest,
            principal,
        ).let(SaveAndUpdateResponse::of)
    }

    @Operation(
        tags = [OpenApiTags.LOCAL_EXAMPLE],
        summary = "공지사항 게시글 삭제 예제",
        description = "(ADMIN) notice-id를 받아 공지사항 게시글을 삭제합니다."
    )
    @DeleteMapping("/notice/{notice-id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteNotice(
        @PathVariable("notice-id") id: Long,
        @AuthenticationPrincipal principal: Principal,
    ): DefaultResponse {
        writeNoticeService.deleteNotice(id, principal)

        return DefaultResponse()
    }

}
