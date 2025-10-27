package skyw96.investments.Kotlin.Config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class SwaggerConfig {

    @Bean
    open fun openApi(): OpenAPI = OpenAPI()
        .servers(
            listOf(
                Server().url("http://localhost:8080")
            )
        )
        .info(
            Info()
                .title("Investment App API")
                .description("API for investment portfolio management")
                .version("1.0")
        )
        .addSecurityItem(SecurityRequirement().addList("JWT"))
        .components(
            io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(
                    "JWT",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER)
                        .name("Authorization")
                )
        )
}