package com.board.kotlin.user.ui

import com.board.kotlin.ControllerTestBaseConfig
import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.dto.request.SessionCreateReq
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(SessionController::class)
internal class SessionControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @MockBean
    private lateinit var userService: UserService

    @ParameterizedTest
    @CsvSource("email@email.com, password", "test@test.com, test")
    fun `Session 생성 Controller`(email: String, password: String) {
        val sessionCreateReq = SessionCreateReq(email, password)

        val objectMapper = ObjectMapper()
        val sessionJson = objectMapper.writeValueAsString(sessionCreateReq)

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson))
                .andExpect(status().isOk)
    }

    @ParameterizedTest
    @CsvSource("'', password", "test, test")
    fun `Session 생성 이메일 Validation Check`(email: String, password: String) {
        val objectMapper = ObjectMapper()
        val sessionJson = objectMapper.writeValueAsString(SessionCreateReq(email, password))

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson))
                .andExpect(status().isBadRequest)
    }

    @ParameterizedTest
    @CsvSource("email@email.com, ''")
    fun `Session 생성 비밀번호 Validation Check`(email: String, password: String) {
        val objectMapper = ObjectMapper()
        val sessionJson = objectMapper.writeValueAsString(SessionCreateReq(email, password))

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson))
                .andExpect(status().isBadRequest)
    }
}