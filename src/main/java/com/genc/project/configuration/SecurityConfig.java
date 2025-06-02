package com.genc.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.genc.project.filters.JwtAuthFilter;
import com.genc.project.services.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/authenticate", "/login", "/do_register", "/index.html", "/courses").permitAll()
                        .requestMatchers("/addCourse", "/updateCourse/**", "/deleteCourse/**", "/instructor/**", "/api/instructor/reports").hasAuthority("instructor")
                        .requestMatchers("/course/**", "/enrolledCourses", "/enroll", "/dashboard", "/submitQuiz").hasAuthority("student")
                        .requestMatchers("/home", "/changePassword").authenticated()).logout(logout -> logout
                                .logoutUrl("/logout") 
                                .logoutSuccessUrl("/login?logout") 
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                            ).exceptionHandling(exceptions -> exceptions
                                    .accessDeniedHandler(accessDeniedHandler()) 
                                    );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // Check if response is already committed before trying to forward/redirect
            if (!response.isCommitted()) {
                response.sendRedirect("/login"); // Or forward to an error view
            } else {
                // Log the error but can't change response now
                System.err.println("Response already committed, cannot handle access denied.");
            }
        };
    }
    
    @Bean
     public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
     }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}