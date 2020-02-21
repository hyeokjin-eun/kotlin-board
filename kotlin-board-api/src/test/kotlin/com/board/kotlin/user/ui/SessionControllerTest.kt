package com.board.kotlin.user.ui

import com.board.kotlin.ControllerTestBaseConfig
import com.board.kotlin.user.domain.dto.request.SessionCreateReq
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext
import java.awt.PageAttributes

@WebMvcTest(SessionController::class)
internal class SessionControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @ParameterizedTest
    @CsvSource("email@email.com, password", "test@test.com, test")
    fun create(email: String, password: String) {
        val sessionCreateReq = SessionCreateReq(email, password)

        val objectMapper = ObjectMapper()
        val sessionJson = objectMapper.writeValueAsString(sessionCreateReq)

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sessionJson))
                .andExpect(status().isOk)
    }
}