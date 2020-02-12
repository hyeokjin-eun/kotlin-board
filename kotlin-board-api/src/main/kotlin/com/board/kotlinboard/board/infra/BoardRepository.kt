package com.board.kotlinboard.board.infra

import com.board.kotlinboard.board.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun save(board: Board): Board

    override fun findById(id: Long): Optional<Board>
}