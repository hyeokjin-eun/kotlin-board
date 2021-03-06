package com.board.kotlin.board.application

import com.board.kotlin.board.domain.Exception.BoardNotFoundException
import com.board.kotlin.board.domain.dto.response.BoardCreateRes
import com.board.kotlin.board.domain.dto.response.BoardDetailRes
import com.board.kotlin.board.domain.dto.response.BoardListRes
import com.board.kotlin.board.domain.dto.response.BoardUpdateRes
import com.board.kotlin.common.domain.entity.Board
import com.board.kotlin.common.infra.BoardRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BoardService(private var boardRepository: BoardRepository) {

    fun create(title: String, content: String):BoardCreateRes {
        val board = boardRepository.save(Board(title, content))
        return BoardCreateRes(board.id!!, board.title, board.content)
    }

    fun detail(id: Long): BoardDetailRes {
        return boardRepository.findById(id)
                .map {
                    return@map BoardDetailRes(it.title, it.content, it.id!!)
                }
                .orElseThrow { BoardNotFoundException() }
    }

    fun list(): List<BoardListRes> {
        return boardRepository.findAll().stream()
                .map { board ->
                    return@map BoardListRes(board.title, board.content, board.id!!)
                }
                .collect(Collectors.toList())
    }

    fun update(id: Long, title: String, content: String): BoardUpdateRes {
        return boardRepository.findById(id)
                .map {
                    it.title = title
                    it.content = content
                    return@map it
                }
                .map {
                    return@map boardRepository.save(Board(it.title, it.content, it.id))
                }
                .map {
                    return@map BoardUpdateRes(it.id!!, it.title, it.content)
                }
                .orElseThrow { BoardNotFoundException() }
    }

    fun delete(id: Long): String {
        return boardRepository.findById(id)
                .map {
                    boardRepository.delete(it)
                }
                .map {
                    return@map "{}"
                }
                .orElseThrow { BoardNotFoundException() }
    }
}