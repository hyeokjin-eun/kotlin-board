package com.board.kotlinboard.board.domain.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class BoardUpdateReq(@JsonProperty var title: String,
                          @JsonProperty var content: String)