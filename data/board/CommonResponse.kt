package com.baum.baas.local.data.board

import com.baum.baas.global.data.DefaultResponse

data class PageResponse<T>(
    val data: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val sort: String,
) : DefaultResponse() {

    companion object {
        fun <T> of(
            data: List<T>,
            currentPage: Int,
            totalPages: Int,
            totalElements: Long,
            size: Int,
            sort: String
        ): PageResponse<T> {
            return PageResponse(
                data = data,
                currentPage = currentPage + 1,
                totalPages = totalPages,
                totalElements = totalElements,
                size = size,
                sort = sort
            )
        }
    }
}

data class SaveAndUpdateResponse(
    val id: Long,
) : DefaultResponse() {
    companion object {
        fun of(id: Long): SaveAndUpdateResponse {
            return SaveAndUpdateResponse(
                id = id
            )
        }
    }
}
