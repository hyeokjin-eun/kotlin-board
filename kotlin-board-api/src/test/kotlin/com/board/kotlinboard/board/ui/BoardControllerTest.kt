package com.board.kotlinboard.board.ui

import com.board.kotlinboard.ControllerTestBaseConfig
import com.board.kotlinboard.board.application.BoardService
import com.board.kotlinboard.board.domain.entity.Board
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

internal class BoardControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @MockBean
    private lateinit var boardService: BoardService

    @ParameterizedTest
    @CsvSource("제목,내용", "어쩌구,저쩌구")
    fun `Board 저장 API`(title: String, content: String) {
        val objectMapper = ObjectMapper()
        val boardJson: String = objectMapper.writeValueAsString(Board(title, content))

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardJson))
                .andExpect(status().isOk)

        verify(boardService).create()
    }

    @Test
    fun `Board 저장 API 파라미터 누락`() {
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}
