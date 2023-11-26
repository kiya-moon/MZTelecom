package com.team.mztelecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 관리 설정
public class SecurityConfig {
	
	@Bean // 빈으로 등록해주면 자동으로 필터에 시큐리티 설정을 커스텀으로 진행 할 수 있음
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		 	// 특정한 경로 허용, 거부 설정 (작성은 람다식)
			.authorizeHttpRequests((auth) -> auth 
					// permitAll() -> 를 사용하면 권한이 없는 모든 사람들이 들어갈 수 있음.
					.requestMatchers("/", "/**.do" , "/js/**", "/css/**","/images/**", "/login").permitAll()
					
					// ADMIN이라는 권한을 가진 사람만 접속 가능
					.requestMatchers("/admin").hasRole("ADMIN")
					
					// ADMIN, USER 권한을 가진 사람만 접속 가능
					.requestMatchers("/myPage/**", "/purRevBoard/purRevWrite").hasAnyRole("ADMIN","USER")
					.anyRequest().authenticated()
		    )
			// 권한이 없는 사람이 접속하려고하면 로그인 페이지로 자동연결 되는데
			// login 페이지가 없으면 시큐리티 자체 로그인 페이지로 넘어감
			.formLogin(formLogin -> formLogin
					.loginPage("/login") 
			)
			.logout((logout) -> logout.logoutUrl("/logout"))
			
			// 소셜 로그인 
			.oauth2Login((oauth2) -> oauth2.loginPage("/login"));

        return http.build();

	}
	
	// 패스워드 암호화 시켜주는 거
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
