package com.board.kotlin.user.application

import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.common.infra.UserRepository
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.board.kotlin.user.domain.dto.response.UserListRes
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService(private var userRepository: UserRepository) {

    fun create(email: String, password: String): UserCreateRes {
        val user = userRepository.save(User(email, password))
        return UserCreateRes(user.id!!, user.email, user.password)
    }

    fun list(): List<UserListRes> {
        return userRepository.findAll().stream()
                .map {
                    return@map UserListRes(it.id!!, it.email, it.password)
                }
                .collect(Collectors.toList())
    }
}
