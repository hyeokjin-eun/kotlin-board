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
    @CsvSource("1, email@email.com, password", "2, test@test.com, test")
    fun `User 저장 Repository`(id: Long, email: String, password: String) {
        val mockUser = User(email, password, id)

        given(userRepository.save(User(email, password))).willReturn(mockUser)

        val newUser = userRepository.save(User(email, password))

        assertThat(newUser.email).isEqualTo(email)
        assertThat(newUser.password).isEqualTo(password)
        assertThat(newUser.id).isNotNull()
    }

    @Test
    fun `User 목록 조회 Repository`() {
        val mockUserList = listOf(
                User("email@email.com", "password", 1L),
                User("test@test.com", "test", 2L),
                User("alvin@test.com", "alvin", 3L)
        )

        given(userRepository.findAll()).willReturn(mockUserList)

        val userList = userRepository.findAll()

        assertThat(userList[0].email).isEqualTo("email@email.com")
        assertThat(userList[1].email).isEqualTo("test@test.com")
        assertThat(userList[2].email).isEqualTo("alvin@test.com")
    }
}