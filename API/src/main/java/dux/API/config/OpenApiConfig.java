package dux.API.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Equipos de Fútbol - Dux",
        version = "1.0",
        description = "Documentación de los endpoints para la gestión de equipos de fútbol."
    )
)
public class OpenApiConfig {
    
}
