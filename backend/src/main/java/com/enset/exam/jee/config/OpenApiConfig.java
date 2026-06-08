package com.enset.exam.jee.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion de Crédits Bancaires API")
                        .description("API Spring Boot pour la gestion des crédits bancaires.")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation().description("ENSET Mohammedia examen"));
    }
}
