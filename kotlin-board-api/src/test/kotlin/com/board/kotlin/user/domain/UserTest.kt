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
    @CsvSource("1, email@email.com, password, kim", "2, test@test.com, test, pack")
    fun `User 객체 생성`(id: Long, email: String, password:  String, name: String) {
        val user = User(email, password, name, id)

        assertThat(user.id).isEqualTo(id)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isEqualTo(password)

        val user2 = User(email, password, name)

        assertThat(user2.email).isEqualTo(email)
        assertThat(user2.password).isEqualTo(password)
    }
}