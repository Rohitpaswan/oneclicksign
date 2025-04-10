package com.app.oneclicksign.authcontroller;

import com.app.oneclicksign.config.GoogleConfig;
import com.app.oneclicksign.exception.InvalidStateException;
import com.app.oneclicksign.exception.OAuthUserInfoException;
import com.app.oneclicksign.model.TokenResponse;
import com.app.oneclicksign.model.UserInfo;
import com.app.oneclicksign.utils.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;


@RestController
public class AuthController {
	
	private final GoogleConfig googleConfig;
	
	public   AuthController(GoogleConfig googleConfig){
		this.googleConfig = googleConfig;
	}
	
	@GetMapping("/login")
	public String login(HttpSession session){
		String state = UUID.randomUUID().toString();
		session.setAttribute("oauth_state", state);
		
		
		// Build the Google authorization URL
		return UriComponentsBuilder.fromHttpUrl(googleConfig.getAuthUri())
				.queryParam("client_id", googleConfig.getClientId())
				.queryParam("redirect_uri", googleConfig.getRedirectUri())
				.queryParam("response_type", "code")
				.queryParam("scope", googleConfig.getScope())
				.queryParam("state", state)
				.queryParam("prompt", "consent")
				.build().toUriString();
		
	}
	
	
	@GetMapping("/callback")
	public RedirectView callback(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String error,
			@RequestParam String state,
			HttpSession session,
			RedirectAttributes redirectAttributes
	) {
		String savedState = (String) session.getAttribute("oauth_state");
		if (!state.equals(savedState)) {
			
			throw new InvalidStateException("Invalid state parameter received");
		}
		
		if (error != null) {
			redirectAttributes.addAttribute("error", error);
			return new RedirectView("/error");
		}
		
		try {
			TokenResponse tokenResponse = Utils.exchangeCodeForToken(code, googleConfig);
			UserInfo userInfo = Utils.fetchUserInfo(tokenResponse.getAccessToken(), googleConfig);
			session.setAttribute("user", userInfo);
			return new RedirectView("/profile");
		}
		
		catch (OAuthUserInfoException e) {
			throw new OAuthUserInfoException("Failed to retrieve user info", e);
		}
		catch (Exception e) {
			redirectAttributes.addAttribute("error", e.getMessage());
			return new RedirectView("/error");
		}
		
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidate session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		// clear cookies
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		// Redirect to homepage or login
		return "/";
	}
	
	
	
}
