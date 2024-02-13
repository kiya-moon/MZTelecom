package com.team.mztelecom.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.repository.CustRepository;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	private final CustRepository custRepository;
	
	@Override
	public UserDetails loadUserByUsername(String custId) throws UsernameNotFoundException {
		CustBas userData = custRepository.findByCustId(custId).orElseThrow(() -> new UsernameNotFoundException("custId(%s) not found".formatted(custId)));
		if (Utiles.isNullOrEmpty(userData)) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
		
		return new User(userData.getCustId(), userData.getPassword(), userData.getAuthorities());
		
	}

}
