package com.team.mztelecom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.repository.CustRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	private final CustRepository custRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String custId) throws UsernameNotFoundException {
		logger.debug("로그인 custId 확인 ::" + custId);
		
		CustBas userData = custRepository.findByCustId(custId).orElseThrow(() -> new UsernameNotFoundException("custId(%s) not found".formatted(custId)));
		
		return new User(userData.getCustId(), userData.getPassword(), userData.getAuthorities());
		
	}

}
