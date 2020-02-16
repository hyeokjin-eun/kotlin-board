package com.board.kotlinboard.board.ui

import com.board.kotlinboard.ControllerTestBaseConfig
import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.Exception.BoardNotFoundException
import com.board.kotlinboard.board.domain.dto.request.BoardUpdateReq
import com.board.kotlinboard.board.domain.dto.response.BoardCreateRes
import com.board.kotlinboard.board.domain.dto.response.BoardDetailRes
import com.board.kotlinboard.board.domain.dto.response.BoardListRes
import com.board.kotlinboard.board.domain.dto.response.BoardUpdateRes
import com.board.kotlinboard.board.domain.entity.Board
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(BoardController::class)
internal class BoardControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @MockBean
    private lateinit var boardService: BoardService

    @ParameterizedTest
    @CsvSource("제목,내용,1", "어쩌구,저쩌구,2")
    fun `Board 저장 Controller`(title: String, content: String, id: Long) {
        val objectMapper = ObjectMapper()
        val boardJson: String = objectMapper.writeValueAsString(Board(title, content))

        given(boardService.create(title, content)).willReturn(BoardCreateRes(id, title, content))

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardJson))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))
                .andExpect(jsonPath("\$.title").value(title))
                .andExpect(jsonPath("\$.content").value(content))

        verify(boardService).create(title, content)
    }

    @Test
    fun `Board 저장 Controller 파라미터 누락`() {
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Board 저장 Controller Size Check`() {
        val title = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
        val content = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                    "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"

        val objectMapper = ObjectMapper()
        val boardJson: String = objectMapper.writeValueAsString(Board(title, content))

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardJson))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Board List 조회 Controller` () {
        val boardList = listOf(
                BoardListRes("제목1", "내용1", 1L),
                BoardListRes("제목2", "내용2", 2L),
                BoardListRes("제목3", "내용3", 3L)
        )

        given(boardService.list()).willReturn(boardList)

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].title").value("제목1"))
                .andExpect(jsonPath("\$.[1].title").value("제목2"))
                .andExpect(jsonPath("\$.[2].title").value("제목3"))

        verify(boardService).list()
    }

    @ParameterizedTest
    @CsvSource("1,제목1, 내용1", "2, 제목2, 내용2")
    fun `Board Detail 조회 Controller`(id: Long, title: String, content: String) {
        val board = BoardDetailRes(title, content, id)

        given(boardService.detail(id)).willReturn(board)

        mockMvc.perform(get("/board/$id"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))
                .andExpect(jsonPath("\$.title").value(title))
                .andExpect(jsonPath("\$.content").value(content))

        verify(boardService).detail(id)
    }

    @ParameterizedTest
    @CsvSource("100", "200")
    fun `Board Detail 조회 Not Found`(id: Long) {
        given(boardService.detail(id)).willThrow(BoardNotFoundException())

        mockMvc.perform(get("/board/$id"))
                .andExpect(status().isNotFound)
    }

    @ParameterizedTest
    @CsvSource("1, 제목 수정1, 내용 수정1", "2, 제목 수정2, 내용 수정2")
    fun `Board 수정 Controller`(id: Long, title: String, content: String) {
        val objectMapper = ObjectMapper()
        val boardJson: String = objectMapper.writeValueAsString(BoardUpdateReq(title, content))

        given(boardService.update(id, title, content)).willReturn(BoardUpdateRes(id, title, content))

        mockMvc.perform(put("/board/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardJson))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))
                .andExpect(jsonPath("\$.title").value(title))
                .andExpect(jsonPath("\$.content").value(content))

        verify(boardService).update(id, title, content)
    }

    @ParameterizedTest
    @CsvSource("1, 제목1, 내용1", "2, 제목2, 내용2")
    fun `Board 삭제 Controller`(id: Long, title: String, content: String) {
        given(boardService.delete(id)).willReturn("{}")

        mockMvc.perform(delete("/board/$id"))
                .andExpect(status().isOk)

        verify(boardService).delete(id)
    }
}
