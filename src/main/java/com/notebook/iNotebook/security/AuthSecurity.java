package com.notebook.iNotebook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

import com.notebook.iNotebook.service.UserService;

//import com.notebook.iNotebook.service.UserService;


@Configuration
@EnableWebSecurity
public class AuthSecurity{

//	@Autowired
//	private UserService userService;
	
//	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//   	 auth.userDetailsService(userService).passwordEncoder(encoder);
//   }
	
	@Bean
	protected AuthenticationManager authenticationManager( UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}
	
//  @Bean
//  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//  	return configuration.getAuthenticationManager();
//  }
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}

	
    @Bean
    protected SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // @formatter:off
        http
//        	.securityMatcher("/**")
        	.csrf(csrf->csrf
        			.disable())
            .authorizeHttpRequests((authorize) -> authorize
            		.requestMatchers("/loginform","/signupform").permitAll()
                    .anyRequest().authenticated()
            )
//			.formLogin(formLogin -> formLogin
//			.loginPage("/loginform").permitAll()
//			)
//            .csrf().ignoringRequestMatchers("/api/**")
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
            .rememberMe(Customizer.withDefaults())
            ;

		return http.build();
	}
    
}
