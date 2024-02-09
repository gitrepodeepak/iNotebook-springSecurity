package com.notebook.iNotebook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;


@Configuration
@EnableWebSecurity
public class AuthSecurity{
	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//   	 auth.userDetailsService(userService).passwordEncoder(encoder);
//   }
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}
	
//	@Bean(name = "authenticationManagerBean")
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
	
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
    protected SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // @formatter:off
        http
//        	.securityMatcher("/**")
        	.csrf(csrf->csrf
        			.disable())
            .authorizeHttpRequests((authorize) -> authorize
            		.requestMatchers("/loginform","/signupform","/login", "/signup").permitAll()
                    .anyRequest().authenticated()
            )
//			.formLogin((formLogin) -> formLogin
//					.loginProcessingUrl("/login")
////////					.loginPage("/loginform").permitAll()
////////					.defaultSuccessUrl("/").permitAll()
//			)
            .securityContext((securityContext) -> securityContext
        			.securityContextRepository(
        					new DelegatingSecurityContextRepository(
								new HttpSessionSecurityContextRepository(),
								new RequestAttributeSecurityContextRepository()
							)
						)
        		)
			.logout((logout) -> logout
					.logoutUrl("/logout").permitAll()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.deleteCookies()
//					.logoutSuccessUrl("/loginform").permitAll()
					)
			.httpBasic(Customizer.withDefaults())
//				.formLogin(Customizer.withDefaults())
//            	.csrf().ignoringRequestMatchers("/api/**")
//            	.rememberMe(Customizer.withDefaults())
            ;

		return http.build();
	}
    
}
