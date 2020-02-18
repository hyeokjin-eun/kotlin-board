package com.board.kotlin.user.application

import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.common.infra.UserRepository
import com.board.kotlin.user.domain.dto.response.UserListRes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class UserServiceTest {

    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userService = UserService(userRepository)
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password", "2, test@test.com, test")
    fun `User 생성 Service`(id: Long, email: String, password: String) {
        given(userRepository.save(User(email, password))).willReturn(User(email, password, id))

        val user = userService.create(email, password)

        verify(userRepository).save(User(email, password))

        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isEqualTo(password)
        assertThat(user.id).isNotNull()
    }

    @Test
    fun `User 목록 조회 Service`() {
        val mockUserList = listOf(
                User("email@email.com", "password", 1L),
                User("test@test.com", "test", 2L),
                User("alvin@test.com", "alvin", 3L)
        )

        given(userRepository.findAll()).willReturn(mockUserList)

        val userList = userService.list()

        verify(userRepository).findAll()

        assertThat(userList[0].email).isEqualTo("email@email.com")
        assertThat(userList[1].email).isEqualTo("test@test.com")
        assertThat(userList[2].email).isEqualTo("alvin@test.com")
    }
}