package com.team.mztelecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.CustomUserDetails;
import com.team.mztelecom.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String custId) throws UsernameNotFoundException {
		
		CustBas userData = userRepository.findByCustId(custId);

        if (userData != null) {
        	
            return new CustomUserDetails(userData);
        }
        
		
		return null;
	}


}
