package com.board.kotlin.common.domain.entity

import javax.persistence.*

@Entity
data class User (
        var email: String,

        var password: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
)