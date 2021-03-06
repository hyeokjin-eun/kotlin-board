package com.board.kotlin.board.domain.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "게시판 상세 응답 모델")
data class BoardDetailRes(@JsonProperty(value = "title")
                          @ApiModelProperty(value = "제목", required = true, dataType = "String", position = 2)
                          var title: String,

                          @JsonProperty(value = "content")
                          @ApiModelProperty(value = "내용", required = true, dataType = "String", position = 3)
                          var content: String,

                          @JsonProperty(value = "id")
                          @ApiModelProperty(value = "식별자", required = true, dataType = "Long", position = 1)
                          var id: Long)