package com.board.kotlin.user.application

import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.common.infra.UserRepository
import com.board.kotlin.common.util.JwtToken
import com.board.kotlin.user.domain.dto.request.UserUpdateReq
import com.board.kotlin.user.domain.dto.response.UserDetailRes
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
import java.util.*

@ExtendWith(SpringExtension::class)
internal class UserServiceTest {

    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var jwtToken: JwtToken

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.userService = UserService(userRepository, jwtToken)
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password, kim", "2, test@test.com, test, pack")
    fun `User 생성 Service`(id: Long, email: String, password: String, name: String) {
        given(userRepository.save(User(email, password, name))).willReturn(User(email, password, name, id))

        val user = userService.create(email, password, name)

        verify(userRepository).save(User(email, password, name))

        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isEqualTo(password)
        assertThat(user.id).isNotNull()
    }

    @Test
    fun `User 목록 조회 Service`() {
        val mockUserList = listOf(
                User("email@email.com", "password", "kim", 1L),
                User("test@test.com", "test", "pack", 2L),
                User("alvin@test.com", "alvin", "alvin", 3L)
        )

        given(userRepository.findAll()).willReturn(mockUserList)

        val userList = userService.list()

        verify(userRepository).findAll()

        assertThat(userList[0].email).isEqualTo("email@email.com")
        assertThat(userList[1].email).isEqualTo("test@test.com")
        assertThat(userList[2].email).isEqualTo("alvin@test.com")
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password, kim", "2, test@test.com, test, pack")
    fun `User 상세 조회 Service`(id: Long, email: String, password: String, name: String) {
        val mockUser = User(email, password, name, id)

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser))

        val userDetail = userService.detail(id)

        verify(userRepository).findById(id)

        assertThat(userDetail.id).isEqualTo(id)
        assertThat(userDetail.email).isEqualTo(email)
        assertThat(userDetail.password).isEqualTo(password)
    }

    @ParameterizedTest
    @CsvSource("1, update@email.com, update, kim", "2, update@test.com, update, pack")
    fun `User 수정 Service`(id: Long, email: String, password: String, name: String) {
        given(userRepository.findById(id)).willReturn(Optional.of(User(email, password, name, id)))
        given(userRepository.save(User(email, password, name, id))).willReturn(User(email, password, name, id))

        val userUpdate = userService.update(id, UserUpdateReq(email, password))

        verify(userRepository).save(User(email, password, name, id))
        verify(userRepository).findById(id)

        assertThat(userUpdate.id).isEqualTo(id)
        assertThat(userUpdate.email).isEqualTo(email)
        assertThat(userUpdate.password).isEqualTo(password)
    }

    @ParameterizedTest
    @CsvSource("1", "2")
    fun `User 삭제 Service`(id: Long) {
        val mockUser = User("email@email.com", "password", "kim", id)

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser))

        val userDelete = userService.delete(id)

        verify(userRepository).findById(id)
        verify(userRepository).delete(mockUser)

        assertThat(userDelete).isEqualTo("{}")
    }

    @ParameterizedTest
    @CsvSource("email@email.com, password", "test@test.com, test")
    fun `User 인증 Service`(email: String, password: String) {

        val user = userService.authenticate(email, password)
    }
}