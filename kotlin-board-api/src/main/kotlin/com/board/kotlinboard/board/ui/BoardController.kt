package com.board.kotlinboard.board.ui

import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.dto.request.BoardCreateReq
import com.board.kotlinboard.board.domain.dto.request.BoardUpdateReq
import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.dto.response.BoardDetailRes
import com.board.kotlinboard.board.domain.dto.response.BoardListRes
import com.board.kotlinboard.board.domain.dto.response.BoardUpdateRes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/board")
class BoardController(@Autowired val boardService: BoardService) {

    @PostMapping("")
    fun create(@Valid @RequestBody boardCreateReq: BoardCreateReq): BoardCreateRes {
        return boardService.create(boardCreateReq.title, boardCreateReq.content)
    }

    @GetMapping("")
    fun list() : List<BoardListRes> {
        return boardService.list()
    }

    @GetMapping("{id}")
    fun detail(@PathVariable id: Long): BoardDetailRes{
        return boardService.detail(id)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody boardUpdateReq: BoardUpdateReq) : BoardUpdateRes {
        return boardService.update(id, boardUpdateReq.title, boardUpdateReq.content)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) : String {
        return boardService.delete(id)
    }
}
