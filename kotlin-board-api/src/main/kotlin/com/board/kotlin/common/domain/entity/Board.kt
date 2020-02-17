package com.board.kotlin.common.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Board(
        var title: String,

        var content: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
)