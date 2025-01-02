package hu.pte.thesistopicbackend;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(Arrays.asList("http://ec2-18-184-55-200.eu-central-1.compute.amazonaws.com:8080"));
                    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
                    return corsConfig;
                })
                .and()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()  // Minden kérést engedélyez
                .and()
                .csrf().disable()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .build();
    }
}
