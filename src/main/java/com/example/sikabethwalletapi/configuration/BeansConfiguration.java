package com.example.sikabethwalletapi.configuration;

import com.example.sikabethwalletapi.exception.UserNotFoundException;
import com.example.sikabethwalletapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 15:11
 * @project demo_security
 */

@Configuration
public class BeansConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .info(new Info().title("SikabethWalletAPI").version("snapshot")
                        .description("""
                                <div><h1>A Wallet API Integrated With PayStack. </h1></div>
                          
                                <h3><b><u>Quick guide</u></b></h3>
                                <div>1. On successful registration. Kindly check your email to get an activation code needed to activate account.</div>
                                <div>2. Next, login with your email and password. To get a JWT token.</div>
                                <div>3. Then, copy JWT token returned and paste at the top right corner button called Authorize.</div>
                                <div>4. You can now proceed with any endpoint call that is protected.</div>
                                
                                """))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
    }

    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }

}
