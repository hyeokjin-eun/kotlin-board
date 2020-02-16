package com.board.kotlinboard.board.ui

import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.dto.request.BoardCreateReq
import com.board.kotlinboard.board.domain.dto.request.BoardUpdateReq
import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.dto.response.BoardDetailRes
import com.board.kotlinboard.board.domain.dto.response.BoardListRes
import com.board.kotlinboard.board.domain.dto.response.BoardUpdateRes
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/board")
@Api(description = "게시판")
class BoardController(@Autowired val boardService: BoardService) {

    @PostMapping("")
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성한다.")
    fun create(@RequestBody @Validated boardCreateReq: BoardCreateReq): BoardCreateRes {
        return boardService.create(boardCreateReq.title, boardCreateReq.content)
    }

    @GetMapping("")
    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회한다.")
    fun list(): List<BoardListRes> {
        return boardService.list()
    }

    @GetMapping("{id}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 상세 정보를 조회한다.")
    fun detail(@PathVariable id: Long): BoardDetailRes {
        return boardService.detail(id)
    }

    @PutMapping("{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    fun update(@PathVariable id: Long, @Validated @RequestBody boardUpdateReq: BoardUpdateReq): BoardUpdateRes {
        return boardService.update(id, boardUpdateReq.title, boardUpdateReq.content)
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    fun delete(@PathVariable id: Long): String {
        return boardService.delete(id)
    }
}
