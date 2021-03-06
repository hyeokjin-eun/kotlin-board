package com.board.kotlin.user.application

import com.board.kotlin.common.domain.entity.User
import com.board.kotlin.common.infra.UserRepository
import com.board.kotlin.common.util.JwtToken
import com.board.kotlin.user.domain.Exception.UserNotFoundException
import com.board.kotlin.user.domain.dto.request.UserUpdateReq
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.board.kotlin.user.domain.dto.response.UserDetailRes
import com.board.kotlin.user.domain.dto.response.UserListRes
import com.board.kotlin.user.domain.dto.response.UserUpdateRes
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService(private val userRepository: UserRepository,
                  private val jwtToken: JwtToken) {

    fun create(email: String, password: String, name: String): UserCreateRes {
        val user = userRepository.save(User(email, password, name))
        return UserCreateRes(user.id!!, user.email, user.password, user.name)
    }

    fun list(): List<UserListRes> {
        return userRepository.findAll().stream()
                .map {
                    return@map UserListRes(it.id!!, it.email, it.password)
                }
                .collect(Collectors.toList())
    }

    fun detail(id: Long): UserDetailRes {
        return userRepository.findById(id)
                .map {
                    return@map UserDetailRes(it.id!!, it.email, it.password)
                }
                .orElseThrow{ UserNotFoundException() }
    }

    fun update(id: Long, userUpdateReq: UserUpdateReq): UserUpdateRes {
        return userRepository.findById(id)
                .map {
                    return@map userRepository.save(User(it.email, it.password, it.name, it.id))
                }
                .map {
                    return@map UserUpdateRes(it.id!!, it.email, it.password)
                }
                .orElseThrow{ UserNotFoundException() }
    }

    fun delete(id: Long): String {
        return userRepository.findById(id)
                .map {
                    userRepository.delete(it)
                    return@map "{}"
                }
                .orElseThrow { UserNotFoundException() }
    }

    fun authenticate(email: String, password: String): String {
        return "test"
    }
}
