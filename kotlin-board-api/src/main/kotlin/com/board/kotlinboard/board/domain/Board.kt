package com.board.kotlinboard.board.domain

import lombok.Builder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Board(
        val title: String,
        val content: String,
        @Id @GeneratedValue
        val id: Long? = null
)