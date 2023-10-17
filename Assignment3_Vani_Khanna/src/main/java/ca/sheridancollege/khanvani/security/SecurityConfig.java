package ca.sheridancollege.khanvani.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
//	@Bean
//	 public InMemoryUserDetailsManager
//	       userDetailsService(PasswordEncoder passwordEncoder) {
//		 
//		 UserDetails user1 = User.withUsername("vendor")
//				             .password(passwordEncoder.encode("vendor")).roles("VENDOR").build();
//		 
//		 UserDetails user2 = User.withUsername("guest")
//	             .password(passwordEncoder.encode("guest")).roles("GUEST").build();
//		 
//		 return new InMemoryUserDetailsManager(user1, user2);
//	 }
//	@Bean
//	 
//	 public PasswordEncoder passwordEncoder()
//	 {
//		 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
//		 return encoder;
//	 }
//	
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder1()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http,PasswordEncoder passwordEncoder) throws Exception {
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
				authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);				
		return authBuilder.build();
	}

	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.csrf().disable();
		 http.headers().frameOptions().disable();
		 http.authorizeHttpRequests((authz) -> 
		         authz.requestMatchers(HttpMethod.GET, "/").permitAll()
				 .requestMatchers("/h2-console/**").permitAll()
				 .requestMatchers("/add").hasRole("VENDOR")
				 .requestMatchers(HttpMethod.GET, "/css/**").permitAll()
				 .requestMatchers("/delete/**").hasRole("VENDOR")
				 .requestMatchers("/edit/**").hasRole("VENDOR")
				 .requestMatchers("/register").permitAll()
				 .requestMatchers(HttpMethod.GET, "/register").permitAll()
				 .requestMatchers(HttpMethod.POST, "/register").permitAll()
				 .requestMatchers(HttpMethod.GET, "/login").permitAll()
				 .requestMatchers(HttpMethod.POST, "/login").permitAll()
				 
				 .anyRequest().authenticated() )
		 .formLogin((formLogin) ->
	        formLogin
	         .loginPage("/login")
	         .defaultSuccessUrl("/home")
	         .failureUrl("/login?failed")
	         .permitAll()
	    		 )
		 .logout((logout) ->
         logout
          .deleteCookies("remove")
          .invalidateHttpSession(true)
          .logoutUrl("/logout")
          .logoutSuccessUrl("/")
          .permitAll()
   		   )
		 
		 .exceptionHandling( (exceptionHandling) -> 
			exceptionHandling.accessDeniedPage("/access-denied")
			);
		 
		 
	        return http.build();
}
}
