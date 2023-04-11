package com.orderpicker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String apiTitle = String.format("%s API", "Order Picker");
        final String apiDescription = """
                    This api is made to improve programming skills with the Java language, Spring Boot framework and MySQL Database, in addition
                    the api has unit test with Junit and Mockito.
                    To begin you must create a user with Role USER or EMPLOYEE and run the different endpoints where you can create products, orders and
                    make deliveries according to the role that chose.\s
                """;
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(
                new Components()
                    .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(new Info().title(apiTitle).version("1.0.0").description(apiDescription));
    }
}
