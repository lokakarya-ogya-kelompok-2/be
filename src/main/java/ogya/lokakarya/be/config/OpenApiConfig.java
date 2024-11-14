package ogya.lokakarya.be.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final @NonNull ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui.html");
    }

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info().title("OGYA REST API").description("REST API for something")
                        .version("1.0.0").termsOfService("ToS")
                        .license(new License().name("Apache License Version 2.0")
                                .url("https:/apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("aku").email("aku@email.com")));
    }

    @Bean
    GroupedOpenApi allApis() {
        return GroupedOpenApi.builder().group("*").pathsToMatch("/**").build();
    }
}
