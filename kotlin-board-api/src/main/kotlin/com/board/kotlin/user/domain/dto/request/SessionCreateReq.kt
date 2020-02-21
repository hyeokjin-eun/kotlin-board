package com.board.kotlin.user.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Builder
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@ApiModel(description = "세션 생성 요청 모델")
@Builder
data class SessionCreateReq(@JsonProperty(value = "email")
                            @ApiModelProperty(value = "이메일", required = true, dataType = "String", position = 1)
                            @field:NotBlank
                            @field:Email
                            var email: String,

                            @JsonProperty(value = "password")
                            @ApiModelProperty(value = "비밀번호", required = true, dataType = "String", position = 2)
                            @field:NotBlank
                            var password: String)
