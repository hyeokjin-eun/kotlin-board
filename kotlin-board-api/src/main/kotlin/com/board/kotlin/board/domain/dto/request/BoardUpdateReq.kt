package com.board.kotlin.board.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

data class BoardUpdateReq(@JsonProperty(value = "title")
                          @ApiModelProperty(value = "제목", required = true, dataType = "String", position = 1)
                          var title: String,

                          @JsonProperty(value = "content")
                          @ApiModelProperty(value = "내용", required = true, dataType = "String", position = 2)
                          var content: String)