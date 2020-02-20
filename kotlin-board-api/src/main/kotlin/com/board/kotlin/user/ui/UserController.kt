package com.board.kotlin.user.ui

import com.board.kotlin.user.application.UserService
import com.board.kotlin.user.domain.dto.request.UserCreateReq
import com.board.kotlin.user.domain.dto.request.UserUpdateReq
import com.board.kotlin.user.domain.dto.response.UserCreateRes
import com.board.kotlin.user.domain.dto.response.UserDetailRes
import com.board.kotlin.user.domain.dto.response.UserListRes
import com.board.kotlin.user.domain.dto.response.UserUpdateRes
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
    @ApiOperation(value = "유저 목록 조회", notes = "유저 목록을 조회한다.")
    fun list(): List<UserListRes> {
        return userService.list()
    }

    @GetMapping("{id}")
    @ApiOperation(value = "유저 상세 조회", notes = "유저 상세 정보를 조회한다.")
    fun detail(@PathVariable id: Long): UserDetailRes {
        return userService.detail(id)
    }

    @PutMapping("{id}")
    @ApiOperation(value = "유저 수정", notes = "유저 정보를 수정한다.")
    fun update(@PathVariable id: Long, @RequestBody @Validated userUpdateReq: UserUpdateReq): UserUpdateRes {
        return userService.update(id, userUpdateReq)
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "유저 삭제", notes = "유저를 삭제한다.")
    fun delete(@PathVariable id:Long): String {
        return userService.delete(id)
    }
}