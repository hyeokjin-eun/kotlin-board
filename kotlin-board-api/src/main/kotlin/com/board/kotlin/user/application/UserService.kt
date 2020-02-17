package com.board.kotlin.user.application

import com.board.kotlin.user.domain.dto.response.UserCreateRes
import org.springframework.stereotype.Service

@Service
class UserService {

    fun create(email: String, password: String): UserCreateRes {
        return UserCreateRes(1L, "email@email.com", "password")
    }
}
