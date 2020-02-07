package com.board.kotlinboard.board.application

import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import org.springframework.stereotype.Service

@Service
class BoardService {
    fun create():BoardCreateRes {
        return BoardCreateRes(1L, "제목", "내용")
    }
}