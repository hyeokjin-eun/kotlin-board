package com.board.kotlin.common.config

import com.google.common.collect.Lists.newArrayList
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ResponseMessage
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.board.kotlin"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageArrayList())
    }

    private fun responseMessageArrayList(): ArrayList<ResponseMessage> {
        return newArrayList(
                ResponseMessageBuilder()
                        .code(500)
                        .message("Internal Server Error")
                        .responseModel(ModelRef("Error"))
                        .build(),
                ResponseMessageBuilder()
                        .code(400)
                        .message("Bad Request")
                        .build(),
                ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden")
                        .build(),
                ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build()
        )
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Kotlin Board Api")
                .description("Kotlin Board Api Document")
                .build()
    }

}