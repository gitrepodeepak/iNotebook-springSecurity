package com.notebook.iNotebook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//import com.notebook.iNotebook.service.UserService;


@Configuration
@EnableWebSecurity
public class AuthSecurity{

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
	@Bean
    UserDetailsService users() {
		UserDetails user = User.builder()
			.username("root")
			.password("{bcrypt}$2a$10$3qZeNlBjsDoJ/1tCSJ40M.m/ov5ImKm5SCvC9Mtw9v5hlhyOcFz0a")
			.roles("USER","ADMIN")
			.build();
		return new InMemoryUserDetailsManager(user);
	}
	
    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // @formatter:off
        http
//        	.securityMatcher("/**")
        	.csrf(csrf->csrf
        			.disable())
            .authorizeHttpRequests((authorize) -> authorize
            		.requestMatchers("/").permitAll()
                    .anyRequest().authenticated()
            )
//			.formLogin(formLogin -> formLogin
//			.loginPage("/api/login").permitAll()
//			)
//            .csrf().ignoringRequestMatchers("/api/**")
			.httpBasic(Customizer.withDefaults())
//			.formLogin(Customizer.withDefaults())
//            .rememberMe(Customizer.withDefaults())
            ;

		return http.build();
	}
    
//    @Bean
//	AuthenticationManager authenticationManager(
//			UserDetailsService userDetailsService,
//			PasswordEncoder passwordEncoder) {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//		return new ProviderManager(authenticationProvider);
//	}

    
    
}
