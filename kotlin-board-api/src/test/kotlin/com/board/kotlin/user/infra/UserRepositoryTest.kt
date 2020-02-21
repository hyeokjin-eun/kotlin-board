package com.board.kotlin.user.infra

import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.common.infra.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
class UserRepositoryTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password, kim", "2, test@test.com, test, pack")
    fun `User 저장 Repository`(id: Long, email: String, password: String, name: String) {
        val mockUser = User(email, password, name, id)

        given(userRepository.save(User(email, password, name))).willReturn(mockUser)

        val newUser = userRepository.save(User(email, password, name))

        assertThat(newUser.email).isEqualTo(email)
        assertThat(newUser.password).isEqualTo(password)
        assertThat(newUser.id).isNotNull()
    }

    @Test
    fun `User 목록 조회 Repository`() {
        val mockUserList = listOf(
                User("email@email.com", "password", "kim", 1L),
                User("test@test.com", "test", "pack", 2L),
                User("alvin@test.com", "alvin", "alvin", 3L)
        )

        given(userRepository.findAll()).willReturn(mockUserList)

        val userList = userRepository.findAll()

        assertThat(userList[0].email).isEqualTo("email@email.com")
        assertThat(userList[1].email).isEqualTo("test@test.com")
        assertThat(userList[2].email).isEqualTo("alvin@test.com")
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, password, kim", "2, test@test.com, test, pack")
    fun `User 상세 조회 Repository`(id: Long, email: String, password: String, name: String) {
        val mockUser = User(email, password, name, id)

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser))

        val userDetail = userRepository.findById(id)

        assertThat(userDetail.get().id).isEqualTo(id)
        assertThat(userDetail.get().email).isEqualTo(email)
        assertThat(userDetail.get().password).isEqualTo(password)
    }
}