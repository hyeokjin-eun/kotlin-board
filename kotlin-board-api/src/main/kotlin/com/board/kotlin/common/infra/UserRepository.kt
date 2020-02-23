package com.board.kotlin.common.infra

import com.board.kotlin.common.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun save(user: User): User
    fun findByEmail(email: String): Optional<User>
}