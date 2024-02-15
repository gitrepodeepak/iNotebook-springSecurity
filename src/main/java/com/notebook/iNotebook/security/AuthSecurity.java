package com.notebook.iNotebook.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.notebook.iNotebook.jwtAuthentication.JwtAuthenticationEntryPoint;
import com.notebook.iNotebook.jwtAuthentication.JwtAuthenticationFilter;
//import org.springframework.security.web.context.DelegatingSecurityContextRepository;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;



@Configuration
@EnableWebSecurity
public class AuthSecurity{
	
	@Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
	
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
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);
	    configuration.setMaxAge(3600L);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
    @Bean
    protected SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // @formatter:off
        http
//        	.securityMatcher("/**")
        	.csrf(csrf->csrf
        			.disable())
        	.cors(cors -> cors
        			.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests((authorize) -> authorize
            		.requestMatchers("/signup", "/auth/login").permitAll()
                    .anyRequest().authenticated()
            )
            
//			.formLogin((formLogin) -> formLogin
//					.loginProcessingUrl("/login")
////				.loginPage("/loginform").permitAll()
////				.defaultSuccessUrl("/").permitAll()
//			)
//            .securityContext((securityContext) -> securityContext
//        			.securityContextRepository(
//        					new DelegatingSecurityContextRepository(
//								new HttpSessionSecurityContextRepository(),
//								new RequestAttributeSecurityContextRepository()
//							)
//						)
//        		)
//            .sessionManagement(sessionManagement->sessionManagement
//	            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//	            .sessionFixation().newSession()
//	            .invalidSessionUrl("/invalidSession.html")
//	            .maximumSessions(1) // Limit the number of sessions per user
//                .maxSessionsPreventsLogin(true); // Deny login for subsequent sessions
//            )
//			.logout((logout) -> logout
//					.logoutUrl("/logout").permitAll()
//					.invalidateHttpSession(true)
//					.clearAuthentication(true)
////					.deleteCookies()
////					.logoutSuccessUrl("/loginform").permitAll()
//					)
//				.formLogin(Customizer.withDefaults())
//            	.csrf().ignoringRequestMatchers("/api/**")
//            	.rememberMe(Customizer.withDefaults())
			.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.httpBasic(Customizer.withDefaults())
			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            ;

		return http.build();
	}
    
}
