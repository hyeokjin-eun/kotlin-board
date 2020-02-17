package com.board.kotlin.user.domain

import com.board.kotlin.common.domain.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class UserTest {

    @ParameterizedTest
    @CsvSource("1, email@email.com, password", "2, test@test.com, test")
    fun `User 객체 생성`(id: Long, email: String, password:  String) {
        val user = User(email, password, id)

        assertThat(user.id).isEqualTo(id)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isEqualTo(password)

        val user2 = User(email, password)

        assertThat(user2.email).isEqualTo(email)
        assertThat(user2.password).isEqualTo(password)
    }
}