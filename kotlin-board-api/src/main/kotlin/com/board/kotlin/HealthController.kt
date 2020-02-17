package com.board.kotlin

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
@Api(description = "서버 체크")
class HealthController {

    @GetMapping("")
    @ApiOperation(value = "서버 체크", notes = "서버 동작 여부를 체크한다.")
    fun health():String {
        return "Hello"
    }
}