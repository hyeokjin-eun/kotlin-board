package com.board.kotlin.user.ui

import com.board.kotlin.common.util.JwtToken
import com.board.kotlin.user.domain.dto.request.SessionCreateReq
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/session")
class SessionController(private val jwtToken: JwtToken) {

    @PostMapping("")
    fun create(@RequestBody @Validated sessionCreateReq: SessionCreateReq): String {
        return jwtToken.create("email@email.com", "kim")
    }
}