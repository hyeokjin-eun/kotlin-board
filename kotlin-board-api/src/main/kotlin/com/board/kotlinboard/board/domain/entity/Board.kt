package com.board.kotlinboard.board.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Board(
        var title: String,
        var content: String,
        @Id @GeneratedValue
        var id: Long? = null
)