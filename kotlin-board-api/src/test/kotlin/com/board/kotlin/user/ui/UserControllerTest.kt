package com.board.kotlin.user.ui

import com.board.kotlin.ControllerTestBaseConfig
import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.Exception.UserNotFoundException
import com.board.kotlin.user.domain.dto.request.UserUpdateReq
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.board.kotlin.user.domain.dto.response.UserDetailRes
import com.board.kotlin.user.domain.dto.response.UserListRes
import com.board.kotlin.user.domain.dto.response.UserUpdateRes
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

    @Test
    fun `User 목록 조회 Controller`() {
        val userList = listOf(
                UserListRes(1L, "email@email.com", "password"),
                UserListRes(2L, "test@test.com", "test"),
                UserListRes(3L, "alvin@test.com", "alvin")
        )

        given(userService.list()).willReturn(userList)

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].email").value("email@email.com"))
                .andExpect(jsonPath("\$.[1].email").value("test@test.com"))
                .andExpect(jsonPath("\$.[2].email").value("alvin@test.com"))

        verify(userService).list()
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password", "2, test@test.com, test")
    fun `User 상세 조회 Controller`(id: Long, email: String, password: String) {
        given(userService.detail(id)).willReturn(UserDetailRes(id, email, password))

        mockMvc.perform(get("/user/$id"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))
                .andExpect(jsonPath("\$.email").value(email))
                .andExpect(jsonPath("\$.password").value(password))

        verify(userService).detail(id)
    }

    @ParameterizedTest
    @CsvSource("404")
    fun `User 상세 조회 User Not Found`(id: Long) {
        given(userService.detail(id)).willThrow(UserNotFoundException())

        mockMvc.perform(get("/user/$id"))
                .andExpect(status().isNotFound)

        verify(userService).detail(id)
    }

    @ParameterizedTest
    @CsvSource("1, update@email.com, updatePassword", "2, update@test.com, updateTest")
    fun `User 수정 Controller`(id: Long, email: String, password: String) {
        val mockUserUpdateRes = UserUpdateRes(id, email, password)

        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(UserUpdateReq(email, password))

        given(userService.update(id, UserUpdateReq(email, password))).willReturn(mockUserUpdateRes)

        mockMvc.perform(put("/user/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.id").value(id))
                .andExpect(jsonPath("\$.email").value(email))
                .andExpect(jsonPath("\$.password").value(password))

        verify(userService).update(id, UserUpdateReq(email, password))
    }

    @ParameterizedTest
    @CsvSource("404, update@email.com, updatePassword")
    fun `User 수정 Controller User Not Found`(id: Long, email: String, password: String) {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(UserUpdateReq(email, password))

        given(userService.update(id, UserUpdateReq(email, password))).willThrow(UserNotFoundException())

        mockMvc.perform(put("/user/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNotFound)

        verify(userService).update(id, UserUpdateReq(email, password))
    }

    @ParameterizedTest
    @CsvSource("1, '', ''", "1, test, password", "1, 'test@test.com', ''")
    fun `User 수정 Controller 필수 파라미터 누락`(id: Long, email: String, password: String) {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(UserUpdateReq(email, password))

        mockMvc.perform(put("/user/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest)
    }
}