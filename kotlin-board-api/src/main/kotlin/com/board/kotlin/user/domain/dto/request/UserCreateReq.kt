package com.board.kotlin.user.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@ApiModel(description = "유저 생성 요청 모델")
data class UserCreateReq(@JsonProperty(value = "email")
                         @ApiModelProperty(value = "이메일", required = true, dataType = "String", position = 1)
                         @field:NotBlank
                         @field:Email
                         var email: String,

                         @JsonProperty(value = "password")
                         @ApiModelProperty(value = "비밀번호", required = true, dataType = "String", position = 2)
                         @field:NotBlank
                         var password: String,

                         @JsonProperty(value = "name")
                         @ApiModelProperty(value = "이름", required = true, dataType= "String", position = 3)
                         @field:NotBlank
                         @field:Pattern(regexp = "^[a-zA-Z]*$")
                         var name: String)