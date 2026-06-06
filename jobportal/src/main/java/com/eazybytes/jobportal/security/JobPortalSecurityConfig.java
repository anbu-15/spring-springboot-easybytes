package com.eazybytes.jobportal.security;

import com.eazybytes.jobportal.security.filter.JwtTokenValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JobPortalSecurityConfig {

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("securedPaths")
    private final List<String> securedPaths;

    @Bean
    public AuthenticationManager authenticationManager() {
        var authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    //Not Recommended
    @Bean
    public UserDetailsService userDetailsService() {
        var user1 = User.builder().username("anbu").password(passwordEncoder().encode("Anbu@123"))
                .roles("USER").build();
        var user2 = User.builder().username("admin").password(passwordEncoder().encode("Admin@123"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) {
        return http.csrf(csrfConfig -> csrfConfig.disable())
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> {
                    publicPaths.forEach(path -> requests.requestMatchers(path).permitAll());
                    securedPaths.forEach((path -> requests.requestMatchers(path).authenticated()));
                    requests.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidatorFilter(publicPaths), BasicAuthenticationFilter.class)
                .formLogin(flc -> flc.disable())
                .httpBasic(withDefaults())
                .build();
    }
//
//
//    @Bean
//    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) {
//        return http.csrf(csrfConfig -> csrfConfig.disable())
//                .authorizeHttpRequests((requests) ->
////                        requests.requestMatchers("/api/companies/public").permitAll()
////                        .requestMatchers("/api/contacts/public").permitAll())
//                        requests.requestMatchers(RegexRequestMatcher.regexMatcher(".*public$")).permitAll()
//                                .requestMatchers("/api/swagger-ui.html",
//                                        "/swagger-ui/**",
//                                        "/api/v3/api-docs/**",
//                                        "/swagger-resource/**",
//                                        "/swagger-ui.html",
//                                        "/webjars/**").permitAll())
//                .formLogin(flc -> flc.disable())
//                .httpBasic(withDefaults())
//                .build();
//    }

//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
//        return http.csrf(csrfConfig -> csrfConfig.disable())
//                .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())
////                .authorizeHttpRequests((requests) -> requests.anyRequest().denyAll())
////                .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//                .formLogin(flc -> flc.disable())
////                .formLogin(withDefaults())
//                .httpBasic(withDefaults())
////                .httpBasic(hbc->hbc.disable())
//                .build();
//    }
}
