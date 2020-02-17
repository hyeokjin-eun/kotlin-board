package com.board.kotlin.common.infra

import com.board.kotlin.common.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun save(board: Board): Board
}