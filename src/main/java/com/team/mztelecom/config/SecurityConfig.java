package com.team.mztelecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.team.mztelecom.service.CustService;

@Configuration
@EnableWebSecurity // 시큐리티 관리 설정
public class SecurityConfig {
	
	@Autowired
	CustService custService;
	
	@Bean // 빈으로 등록해주면 자동으로 필터에 시큐리티 설정을 커스텀으로 진행 할 수 있음
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		 	// 특정한 경로 허용, 거부 설정 (작성은 람다식)
			.authorizeHttpRequests((auth) -> auth 
					// ADMIN이라는 권한을 가진 사람만 접속 가능
					.requestMatchers("/admin").hasRole("ADMIN")
					
					// ADMIN, USER 권한을 가진 사람만 접속 가능
					.requestMatchers("/myPage/**", "/purRevBoard/purRevWrite").hasAnyRole("ADMIN","USER")

					// permitAll() -> 를 사용하면 권한이 없는 모든 사람들이 들어갈 수 있음.
					.requestMatchers("/**" , "/js/**", "/css/**","/images/**", "/login").permitAll()
					
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
	
	@Bean
	AuthenticationManager authenticationManager
   (HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
		   UserDetailsService userDetailsService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
       
				//사용자 정보를 가져올 서비스 설정: 반드시 custService를 상속받은 클래스여야 함
				.userDetailsService(custService)
               
               //비밀번호 암호화를 위한 인코더 설정
				.passwordEncoder(bCryptPasswordEncoder)
               
				.and()
				.build();
	}
	
}
