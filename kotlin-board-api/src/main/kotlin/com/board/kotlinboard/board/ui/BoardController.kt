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

    @GetMapping("{id}")
    fun detail(@PathVariable id: Long): BoardDetailRes = boardService.detail(id)

    @PostMapping("")
    fun create(@Valid @RequestBody boardCreateReq: BoardCreateReq): BoardCreateRes = boardService.create(boardCreateReq.title, boardCreateReq.content)

    @GetMapping("")
    fun list() : List<BoardListRes> = boardService.list()

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody boardUpdateReq: BoardUpdateReq) : BoardUpdateRes = boardService.update(id, boardUpdateReq)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) : String = boardService.delete(id)
}
