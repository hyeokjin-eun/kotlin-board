package com.board.kotlinboard.board.ui

import com.board.kotlinboard.board.domain.dto.request.BoardCreateReq
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/board")
class BoardController {

    @PostMapping("")
    fun create(@Valid @RequestBody boardCreateReq: BoardCreateReq): BoardCreateReq = boardCreateReq
}
