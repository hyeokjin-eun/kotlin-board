package com.board.kotlinboard.board.infra

import com.board.kotlinboard.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
}