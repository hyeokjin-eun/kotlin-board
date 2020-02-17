package com.board.kotlin.user.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class UserCreateReq(@JsonProperty(value = "email")
                         @ApiModelProperty(value = "이메일", required = true, dataType = "String", position = 1)
                         @field:NotEmpty
                         @field:Email
                         var email: String,

                         @JsonProperty(value = "password")
                         @ApiModelProperty(value = "비밀번호", required = true, dataType = "String", position = 2)
                         @field:NotEmpty
                         var password: String)