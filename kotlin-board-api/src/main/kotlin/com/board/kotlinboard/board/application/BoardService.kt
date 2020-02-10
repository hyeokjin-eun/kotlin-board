package com.board.kotlinboard.board.application

import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.dto.response.BoardDetailRes
import com.board.kotlinboard.board.domain.dto.response.BoardListRes
import com.board.kotlinboard.board.domain.entity.Board
import com.board.kotlinboard.board.infra.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BoardService(@Autowired private var boardRepository: BoardRepository) {

    fun create(title: String, content: String):BoardCreateRes {
        val board:Board? = boardRepository.save(Board(title, content))
        return BoardCreateRes(board?.id!!, board.title, board.content)
    }

    fun detail(id: Long): BoardDetailRes {
        return boardRepository.findById(id)
                .map { board ->
                    return@map BoardDetailRes(board.title, board.content, board.id!!)
                }
                .orElse(null)
    }

    fun list(): List<BoardListRes> {
        return boardRepository.findAll().stream()
                .map { board ->
                    return@map BoardListRes(board.title, board.content, board.id!!)
                }
                .collect(Collectors.toList())
    }
}