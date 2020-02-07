package com.board.kotlinboard.board.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

data class BoardCreateReq (@NotEmpty @JsonProperty("title") var title: String,
                           @NotEmpty @JsonProperty("content") var content: String)
