package com.board.kotlin

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(SpringExtension::class)
open class ControllerTestBaseConfig(@Autowired private var webApplicationContext: WebApplicationContext) {

    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
                .build()
    }
}