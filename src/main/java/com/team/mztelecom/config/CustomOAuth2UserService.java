package com.team.mztelecom.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.service.CustService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private CustService custService;
	
	 @Override
	 @Transactional
	 public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		 OAuth2User oAuth2User = super.loadUser(userRequest);
		 
		 String oauthId = oAuth2User.getName();
		 
		 String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
		 
		 String username = providerTypeCode + "__%s".formatted(oauthId);
		 
		 
		 return null;
	 }
}
