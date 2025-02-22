package ogya.lokakarya.be.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
                        .contact(new Contact().name("aku").email("aku@email.com")))
                .servers(List.of(new Server().url("/")));
    }

    @Bean
    GroupedOpenApi allApis() {
        return GroupedOpenApi.builder().group("*").pathsToMatch("/**").build();
    }

    @Bean
    GroupedOpenApi allUsers() {
        return GroupedOpenApi.builder().group("Users").pathsToMatch("/users/**").build();
    }

    @Bean
    GroupedOpenApi allTechnicalSkills() {
        return GroupedOpenApi.builder().group("Technical Skills").pathsToMatch("/technical-skills/**").build();
    }

    @Bean
    GroupedOpenApi allRoles() {
        return GroupedOpenApi.builder().group("Roles").pathsToMatch("/roles/**").build();
    }

    @Bean
    GroupedOpenApi allMenus() {
        return GroupedOpenApi.builder().group("Menus").pathsToMatch("/menus/**").build();
    }

    @Bean
    GroupedOpenApi allGroupAttitudeSkills() {
        return GroupedOpenApi.builder().group("Group Attitude Skills").pathsToMatch("/group-attitude-skills/**").build();
    }
    @Bean
    GroupedOpenApi allGroupAchievements() {
        return GroupedOpenApi.builder().group("Group Achievements").pathsToMatch("/group-achievements/**").build();
    }
    @Bean
    GroupedOpenApi allEmpSuggestions() {
        return GroupedOpenApi.builder().group("Emp Suggestions").pathsToMatch("/emp-suggestions/**").build();
    }
    @Bean
    GroupedOpenApi allDivisions() {
        return GroupedOpenApi.builder().group("Divisions").pathsToMatch("/divisions/**").build();
    }
    @Bean
    GroupedOpenApi allDevPlans() {
        return GroupedOpenApi.builder().group("Dev Plans").pathsToMatch("/dev-plans/**").build();
    }
    @Bean
    GroupedOpenApi allAttitudeSkills() {
        return GroupedOpenApi.builder().group("Attitude Skills").pathsToMatch("/attitude-skills/**").build();
    }
    @Bean
    GroupedOpenApi allAssessmentSummaries() {
        return GroupedOpenApi.builder().group("Assessment Summaries").pathsToMatch("/assessment-summaries/**").build();
    }
    @Bean
    GroupedOpenApi auth() {
        return GroupedOpenApi.builder().group("auth").pathsToMatch("/auth/**").build();
    }
    @Bean
    GroupedOpenApi AllAchievements() {
        return GroupedOpenApi.builder().group("Achievements").pathsToMatch("/achievements/**").build();
    }
    @Bean
    GroupedOpenApi AllAchievementSkills() {
        return GroupedOpenApi.builder().group("Emp Achievement Skills").pathsToMatch("/emp-achievement-skills/**").build();
    }
    @Bean
    GroupedOpenApi AllEmpAttitudeSkills() {
        return GroupedOpenApi.builder().group("Emp Attitude Skills").pathsToMatch("/emp-attitude-skills/**").build();
    }
    @Bean
    GroupedOpenApi AllEmpDevPlans() {
        return GroupedOpenApi.builder().group("Emp Dev Plans").pathsToMatch("/emp-dev-plans/**").build();
    }
    @Bean
    GroupedOpenApi AllEmpTechnicalSkills() {
        return GroupedOpenApi.builder().group("Emp Technical Skills").pathsToMatch("/emp-technical-skills/**").build();
    }
}
