package learn_mate_it.dev.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(title = "LearnMate", version = "v1.0"),
    servers = [Server(url = "\${server.url}")]
)

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val securityRequirement = SecurityRequirement().addList("bearerAuth")
        val components = Components()
            .addSecuritySchemes(
                "bearerAuth",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .`in`(SecurityScheme.In.HEADER)
                    .name("Authorization")
            )
        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}