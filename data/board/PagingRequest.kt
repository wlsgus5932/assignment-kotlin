package com.baum.baas.local.data.board

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PagingRequest(
    val currentPage: Int = 1,
    val size: Int = 10,
    val sort: String = "id,DESC"
) {
    fun toPageable(): Pageable {
        val validateSort = this.sort.split(",").let {
            Sort.by(Sort.Direction.fromString(it[1]), it[0])
        }

        return PageRequest.of(
            this.currentPage - 1, this.size, validateSort
        )
    }
}