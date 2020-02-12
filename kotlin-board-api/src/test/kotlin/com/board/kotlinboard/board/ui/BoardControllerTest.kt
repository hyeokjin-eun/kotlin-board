package com.board.kotlinboard.board.ui

import com.board.kotlinboard.ControllerTestBaseConfig
import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.dto.response.BoardDetailRes
import com.board.kotlinboard.board.domain.dto.response.BoardListRes
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(BoardController::class)
internal class BoardControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @MockBean
    private lateinit var boardService: BoardService

    @ParameterizedTest
    @CsvSource("제목,내용", "어쩌구,저쩌구")
    fun `Board 저장 Controller`(title: String, content: String) {
        val objectMapper = ObjectMapper()
        val boardJson: String = objectMapper.writeValueAsString(Board(title, content))

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardJson))
                .andExpect(status().isOk)

        verify(boardService).create(title, content)
    }

    @Test
    fun `Board 저장 Controller 파라미터 누락`() {
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON))
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
    @CsvSource("1", "2")
    fun `Board Detail 조회 Controller`(id: Long) {
        val board = BoardDetailRes("제목1", "제목2", id)

        given(boardService.detail(id)).willReturn(board)

        mockMvc.perform(get("/board/$id"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))

        verify(boardService).detail(id)
    }
}
