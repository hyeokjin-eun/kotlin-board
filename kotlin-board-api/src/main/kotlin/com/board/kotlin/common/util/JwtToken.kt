package com.board.kotlin.common.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key

open class JwtToken(secret: String) {

    private var key:Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun create(email: String, name: String): String {
        return Jwts.builder()
                .claim("email", email)
                .claim("name", name)
                .signWith(key,  SignatureAlgorithm.HS256)
                .compact()
    }

    fun getClaims(token: String?): Claims? {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
    }
}
