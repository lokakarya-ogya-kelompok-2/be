package ogya.lokakarya.be.config.security;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import lombok.RequiredArgsConstructor;
import ogya.lokakarya.be.config.security.jwt.filter.JwtValidationFilter;
import ogya.lokakarya.be.service.AuthService;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtValidationFilter jwtValidationFilter;

    @Bean
    SecurityFilterChain mySecurityConfig(HttpSecurity http, AuthService authSvc) throws Exception {
        return http
                .sessionManagement(sessionmangement -> sessionmangement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration cfg = new CorsConfiguration();
                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));
                    return cfg;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").authenticated()
                        .requestMatchers("/divisions/**").authenticated()
                        .requestMatchers("/dev-plans/**").authenticated()
                        .requestMatchers("/emp-attitude-skills/**").authenticated()
                        .requestMatchers("/emp-dev-plans/**").authenticated()
                        .requestMatchers("/menus/**").authenticated()
                        .requestMatchers("/roles/**").authenticated()
                        .requestMatchers("/role-menu/**").authenticated()
                        .requestMatchers("/achievements/**").authenticated()
                        .requestMatchers("/attitude-skills/**").authenticated()
                        .requestMatchers("/assessment-summaries/**").authenticated()
                        .requestMatchers("/emp-technical-skills/**").authenticated()
                        .requestMatchers("/emp-achievement-skills/**").authenticated()
                        .requestMatchers("/emp-suggestions/**").authenticated()
                        .requestMatchers("/technical-skills/**").authenticated()
                        .requestMatchers("/group-achevements/**").authenticated()
                        .requestMatchers("/group-attitude-skills/**").authenticated().anyRequest()
                        .permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(authSvc).build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
