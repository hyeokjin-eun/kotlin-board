package com.board.kotlinboard.board.domain.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BoardListRes (@JsonProperty var title: String,
                         @JsonProperty var content: String,
                         @JsonProperty var id: Long)