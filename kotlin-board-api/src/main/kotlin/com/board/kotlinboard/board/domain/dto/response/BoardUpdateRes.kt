package com.board.kotlinboard.board.domain.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BoardUpdateRes(@JsonProperty var id: Long,
                          @JsonProperty var title: String,
                          @JsonProperty var content: String)