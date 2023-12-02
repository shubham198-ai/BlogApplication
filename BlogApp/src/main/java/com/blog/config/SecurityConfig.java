package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JWTAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	public static final String[] PUBLIC_URLS= {
			"/auth/login",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/webjars/**"
	};
	@Autowired
	private CustomUserDetailService customService;
	@Autowired
	private JWTAuthenticationEntryPoint authPoint;
	  @Autowired 
	  private JwtAuthenticationFilter filter;
	 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
		  http.
		  csrf(csrf -> csrf.disable())
		  .authorizeRequests(requests -> requests.
		  antMatchers("/app")
		  .authenticated()
		  .antMatchers(PUBLIC_URLS)
		  .permitAll().antMatchers(HttpMethod.GET).permitAll()
		  .anyRequest() 
		  .authenticated())
		  .exceptionHandling(ex ->
		  ex.authenticationEntryPoint(authPoint))
		  .sessionManagement(session ->
		  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		  http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		  return http.build();
		 
    	
		/*
		 * http.csrf(csrf -> csrf.disable()) .authorizeHttpRequests() .anyRequest()
		 * .authenticated() .and()
		 * 
		 * .httpBasic(withDefaults())); http.authenticationProvider(authProvider());
		 */
       // return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
    	DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    	provider.setUserDetailsService(this.customService);
    	provider.setPasswordEncoder(passwordEncoder());
		return provider;
    	
   
   

		
    
  }
}