package com.board.kotlin.user.ui

import com.board.kotlin.common.infra.UserRepository
import com.board.kotlin.common.util.JwtToken
import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.dto.request.SessionCreateReq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("session")
class SessionController(val userService: UserService) {

    @PostMapping("")
    fun create(@RequestBody @Validated sessionCreateReq: SessionCreateReq): String {
        return userService.authenticate(sessionCreateReq.email, sessionCreateReq.password)
    }
}