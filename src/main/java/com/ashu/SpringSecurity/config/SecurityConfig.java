package com.ashu.SpringSecurity.config;

import com.ashu.SpringSecurity.entity.Permissions;
import com.ashu.SpringSecurity.entity.Role;
import com.ashu.SpringSecurity.entity.Users;
import com.ashu.SpringSecurity.filter.JwtAuthFilter;
import com.ashu.SpringSecurity.repository.UserDetailsRepository;
import com.ashu.SpringSecurity.service.CustomUserDetailsService;
import com.ashu.SpringSecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

   private final JwtAuthFilter jwtAuthFilter;
   private final UserDetailsService userDetailsService;
   private final JWTUtil jwtUtil;
   private final UserDetailsRepository userDetailsRepository;

   public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService, JWTUtil jwtUtil, UserDetailsRepository userDetailsRepository){
       this.jwtAuthFilter = jwtAuthFilter;
       this.userDetailsService = userDetailsService;
       this.jwtUtil = jwtUtil;
       this.userDetailsRepository = userDetailsRepository;
   }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JWTUtil jWTUtil) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers(("/api/users/register")).permitAll()
                                .requestMatchers("/oauth").authenticated()
//                        .requestMatchers(HttpMethod.GET,"/weather/**").hasAuthority(Permissions.WEATHER_READ.name())
//                        .requestMatchers(HttpMethod.POST,"/weather/**").hasAuthority(Permissions.WEATHER_WRITE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/weather/**").hasAuthority(Permissions.WEATHER_DELETE.name())
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {

                            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

                            // Step 1: Get email
                            String username = oAuth2User.getAttribute("email");

                            //Step 2: Save user if not exists
                            Optional<Users> userOpt = userDetailsRepository.findByUsername(username);

                            if (userOpt.isEmpty()) {
                                Users user = new Users();
                                user.setUsername(username);
                                user.setRole(Role.USER);
                                userDetailsRepository.save(user);
                            }

                            // Step 3: Generate JWT
                            String token = jwtUtil.generateToken(username);

                            // Step 4: Return token
                            response.setContentType("application/json");
                            response.getWriter().write("{\"token\":\"" + token + "\"}");
                        })
                )
        ;
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }
}