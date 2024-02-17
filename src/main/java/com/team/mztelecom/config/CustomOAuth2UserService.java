package com.team.mztelecom.config;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.service.CustService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustService.class);
	
	private final CustService custService;
	
	 @Override
	 @Transactional
	 public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		 OAuth2User oAuth2User = super.loadUser(userRequest);
		 Map<String, Object> attributes = oAuth2User.getAttributes();
		 
		 logger.debug("소셜로그인 service");
		 logger.debug("Attributes: " + attributes.toString());
		 
		 // kakao
		 Map attributesProperties = (Map) attributes.get("properties");
		 Map attributesAccount = (Map) attributes.get("kakao_account");
		 
		 String custNm = "Unknown";
		 String custEmail = "Unknown";
		 
		 if(attributesProperties != null) {
			 custNm = (String) attributesProperties.get("nickname");
		 } else if(attributes != null) {
			 custNm = (String) attributes.get("name");
		 }
		 
		 if(attributesAccount != null) {
			 custEmail = (String) attributesAccount.get("email");
		 } else if(attributes != null) {
			 custEmail = (String) attributes.get("email");
		 }
		 
	     
		 String oauthId = oAuth2User.getName();
		 
		 String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
		 
		 String custId = providerTypeCode + "_%s".formatted(oauthId);
		 
		 CustBas custBas = custService.whenSocialLogin(providerTypeCode, custId, custNm, custEmail);
		 
		 return new CustomOAuth2User(custBas.getCustId(), custBas.getPassword(), custBas.getAuthorities());
	 }
}

class CustomOAuth2User extends User implements OAuth2User {

    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
