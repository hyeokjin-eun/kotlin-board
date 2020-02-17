package com.board.kotlin.user.ui

import com.board.kotlin.ControllerTestBaseConfig
import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(UserController::class)
internal class UserControllerTest(webApplicationContext: WebApplicationContext) : ControllerTestBaseConfig(webApplicationContext) {

    @MockBean
    private lateinit var userService: UserService

    @ParameterizedTest
    @CsvSource("1, email@email.com, password", "2, test@test.com, test")
    fun `User 저장 Controller`(id: Long, email: String, password: String) {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(User(email, password))

        given(userService.create(email, password)).willReturn(UserCreateRes(id, email, password))

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.email").value(email))
                .andExpect(jsonPath("\$.password").value(password))

        verify(userService).create(email, password)
    }

    @ParameterizedTest
    @CsvSource("email, password")
    fun `User 저장 Controller 이메일 형식 오류`(email: String, password: String) {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(User(email, password))

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest)
    }

    @ParameterizedTest
    @CsvSource("email@email.com, ''")
    fun `User 저장 Controller 비밀번호 누락`(email: String, password: String) {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(User(email, password))

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest)
    }
}