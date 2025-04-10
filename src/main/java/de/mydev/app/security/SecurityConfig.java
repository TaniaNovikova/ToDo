package de.mydev.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // Конструкторное внедрение зависимостей
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // Определяем bean для конфигурации цепочки фильтров безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                // Настройка CORS (используется настройки из отдельного bean-а)
                .cors(Customizer.withDefaults())
                // Конфигурация авторизации запросов
                .authorizeHttpRequests(authorize -> authorize
                        // Публичные пути
                        .requestMatchers("/error", "/api/user/**").permitAll()
                        // Доступ только для роли ADMIN (при этом ADMIN автоматически становится ROLE_ADMIN)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                // Настройка logout: запрос на logout по указанному URL с методом POST
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST"))
                        .permitAll()
                )
                // Конфигурация страницы логина
                .formLogin(form -> form
                        .loginPage("/api/user/login")
                )
                // Включаем базовую аутентификацию
                .httpBasic(Customizer.withDefaults())
                // Определяем политику управления сессиями как STATELESS (без сохранения состояния)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Отключаем CSRF (актуально для stateless API)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // Регистрируем AuthenticationManager через AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Регистрируем провайдер аутентификации для использования userDetailsService и passwordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Конфигурация CORS для Spring MVC
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Разрешаем любые источники и методы для всех путей (при необходимости уточните правила доступа)
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
}
