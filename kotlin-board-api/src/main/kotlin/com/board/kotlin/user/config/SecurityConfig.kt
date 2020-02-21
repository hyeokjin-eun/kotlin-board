package com.board.kotlin.user.config

import com.board.kotlin.common.util.JwtToken
import com.board.kotlin.user.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.servlet.Filter

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val filter: Filter = JwtAuthenticationFilter(authenticationManager(), jwtToken())
        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers()
                .frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtToken(): JwtToken {
        return JwtToken(secret)
    }
}