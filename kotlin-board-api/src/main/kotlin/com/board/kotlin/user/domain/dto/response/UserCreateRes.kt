package com.board.kotlin.user.domain.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "유저 생성 응답 모델")
data class UserCreateRes (@JsonProperty(value = "id")
                          @ApiModelProperty(value = "식별자", required = true, dataType = "Long", position = 1)
                          var id: Long,

                          @JsonProperty(value = "email")
                          @ApiModelProperty(value = "이메일", required = true, dataType = "String", position = 2)
                          var email: String,

                          @JsonProperty(value = "password")
                          @ApiModelProperty(value = "비밀번호", required = true, dataType = "String", position = 3)
                          var password: String,

                          @JsonProperty(value = "name")
                          @ApiModelProperty(value = "이름", required = true, dataType = "String", position = 4)
                          var name: String)