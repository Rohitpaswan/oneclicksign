package com.app.oneclicksign.authcontroller;

import com.app.oneclicksign.config.GoogleConfig;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
public class AuthController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final GoogleConfig googleConfig;
	
	public   AuthController(GoogleConfig googleConfig){
		this.googleConfig = googleConfig;
	}
	
	@GetMapping("/login")
	public String login(HttpSession session){
		String state = UUID.randomUUID().toString();
		session.setAttribute("oauth_state", state);
		logger.info(state);
		
		// Build the Google authorization URL
		String authUrl = UriComponentsBuilder.fromHttpUrl(googleConfig.getAuthUri())
				.queryParam("client_id", googleConfig.getClientId())
				.queryParam("redirect_uri", googleConfig.getRedirectUri())
				.queryParam("response_type", "code")
				.queryParam("scope", googleConfig.getScope())
				.queryParam("state", state)
				.build().toUriString();
		
		return  authUrl;
		
		
	}
	
	
}
