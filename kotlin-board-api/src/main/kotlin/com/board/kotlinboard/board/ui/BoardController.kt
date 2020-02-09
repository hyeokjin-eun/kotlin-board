package com.board.kotlinboard.board.ui

import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.dto.request.BoardCreateReq
import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.entity.Board
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/board")
class BoardController(@Autowired val boardService: BoardService) {

    @GetMapping("{id}")
    fun detail(@PathVariable id: Long): Board = boardService.detail(id)

    @PostMapping("")
    fun create(@Valid @RequestBody boardCreateReq: BoardCreateReq): BoardCreateRes = boardService.create(boardCreateReq.title, boardCreateReq.content)
}
