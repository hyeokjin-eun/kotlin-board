package com.board.kotlinboard.board.domain.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BoardCreateRes(@JsonProperty("id") var id: Long,
                          @JsonProperty("title") var title: String,
                          @JsonProperty("content") var content: String)