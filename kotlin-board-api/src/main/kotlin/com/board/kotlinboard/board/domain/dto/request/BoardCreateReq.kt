package com.board.kotlinboard.board.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kotlin.math.min

@ApiModel(description = "게시판 생성 요청 모델")
data class BoardCreateReq (@JsonProperty(value = "title")
                           @ApiModelProperty(value = "제목", required = true, dataType = "String")
                           @field:NotEmpty
                           @field:Size(min = 1, max = 100)
                           var title: String,

                           @JsonProperty(value = "content")
                           @ApiModelProperty(value = "내용", required = true)
                           @field:NotEmpty
                           @field:Size(min = 1, max = 200)
                           var content: String)
