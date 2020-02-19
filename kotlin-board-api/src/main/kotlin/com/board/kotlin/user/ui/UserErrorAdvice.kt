package com.board.kotlin.user.ui

import com.board.kotlin.board.domain.Exception.BoardNotFoundException
import com.board.kotlin.user.domain.Exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class UserErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(): String = "{}"
}