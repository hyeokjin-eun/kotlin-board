package com.board.kotlinboard.board.infra

import com.board.kotlinboard.board.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long>