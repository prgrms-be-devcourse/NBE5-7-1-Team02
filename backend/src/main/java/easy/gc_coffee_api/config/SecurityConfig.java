package easy.gc_coffee_api.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(
                        form -> {
                            form.usernameParameter("id")
                                .passwordParameter("password")
                                .successHandler((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                })
                                .failureHandler((request, response, exception) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                })
                                .loginProcessingUrl("/admin/login")
                                .permitAll();
                        }
                )
                .logout(logout ->
                        logout.logoutUrl("/admin/logout")
                            .logoutSuccessHandler((request, response, authentication) -> {
                                response.setStatus(HttpServletResponse.SC_OK);
                            })
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session ->
                        session.maximumSessions(1)
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"Unauthorized\"}");
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/orders").permitAll()
                        .anyRequest().authenticated()
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}