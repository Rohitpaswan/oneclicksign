package com.app.oneclicksign.utils;

import com.app.oneclicksign.config.GoogleConfig;
import com.app.oneclicksign.model.TokenResponse;
import com.app.oneclicksign.model.UserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Utils {
	
	private Utils(){
	
	}
	
	//give token response
	public static TokenResponse exchangeCodeForToken(String code, GoogleConfig googleConfig) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", googleConfig.getClientId());
		params.add("client_secret", googleConfig.getClientSecret());
		params.add("code", code);
		params.add("redirect_uri", googleConfig.getRedirectUri());
		params.add("grant_type", "authorization_code");
		
		return new RestTemplate().postForObject(
				googleConfig.getTokenUri(),
				new HttpEntity<>(params, headers),
				TokenResponse.class
		);
	}
	
	public static UserInfo fetchUserInfo(String accessToken, GoogleConfig googleConfig) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		
		return new RestTemplate().exchange(
				googleConfig.getUserinfoUri(),
				HttpMethod.GET,
				new HttpEntity<>(headers),
				UserInfo.class
		).getBody();
	}
	
	
}
