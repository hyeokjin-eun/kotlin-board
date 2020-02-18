package com.board.kotlin.user.ui

import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.dto.request.UserCreateReq
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.board.kotlin.user.domain.dto.response.UserListRes
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
@Api(description = "유저", position = 1)
class UserController(val userService: UserService) {

    @PostMapping("")
    @ApiOperation(value = "유저 생성", notes = "유저 회원 가입")
    fun create(@RequestBody @Validated userCreateReq: UserCreateReq): UserCreateRes {
        return userService.create(userCreateReq.email, userCreateReq.password)
    }

    @GetMapping("")
    @ApiOperation(value = "유저 목록 조회", notes = "유저 목록 조회")
    fun list(): List<UserListRes> {
        return userService.list()
    }
}