package com.board.kotlinboard.board.application

import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.entity.Board
import com.board.kotlinboard.board.infra.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardService(@Autowired
                   private var boardRepository: BoardRepository) {

    fun create(title: String, content: String):BoardCreateRes {
        val board:Board? = boardRepository.save(Board(title, content))
        return BoardCreateRes(board!!.id!!, board.title, board.content)
    }
}