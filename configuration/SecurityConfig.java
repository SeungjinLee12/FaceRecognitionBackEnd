package com.board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
				.authorizeHttpRequests(request -> request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers("/api/board/android/checkDuplicate", "/api/board/android/join",
								"/api/loginApp", "/api/loginWeb")
						.permitAll().anyRequest().anonymous())	
				.formLogin(login -> login.loginPage("/loginWeb").loginPage("/loginAPP") // [A] 커스텀 로그인 페이지 지정
						.usernameParameter("ID") // [C] submit할 아이디
						.passwordParameter("PASSWORD") // [D] submit할 비밀번호
						.defaultSuccessUrl("/**", true).permitAll())
				.logout();
		
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}