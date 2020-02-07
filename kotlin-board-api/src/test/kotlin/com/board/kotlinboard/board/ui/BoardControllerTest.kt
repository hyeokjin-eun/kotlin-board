package com.board.kotlinboard.board.ui

import com.board.kotlinboard.ControllerTestBaseConfig
import com.board.kotlinboard.board.domain.entity.Board
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

internal class BoardControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @Test
    fun `Board 저장 API`() {
        val title = "제목"
        val content = "내용"
        val board = Board(title, content)

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"제목\",\"content\":\"내용\"}"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("\"title\":\"제목\"")))
                .andExpect(content().string(containsString("\"content\":\"내용\"")))
    }
}
