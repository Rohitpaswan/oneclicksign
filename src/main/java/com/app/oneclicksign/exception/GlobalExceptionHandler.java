package com.app.oneclicksign.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(InvalidStateException.class)
	public RedirectView handleInvalidState(InvalidStateException ex, RedirectAttributes attrs) {
		attrs.addFlashAttribute("errorCode", "401");
		attrs.addFlashAttribute("errorMessage", "Security Error: " + ex.getMessage());
		return new RedirectView("/error");
	}
	
	@ExceptionHandler(OAuthUserInfoException.class)
	public RedirectView handleOAuthUserInfo(OAuthUserInfoException ex, RedirectAttributes attrs){
		attrs.addFlashAttribute("errorCode", "404");
		attrs.addFlashAttribute("errorMessage", "User not found");
		return new RedirectView("/error");
	}
	
	
	@ExceptionHandler(Exception.class)
	public RedirectView handleGenericErrors(Exception ex, RedirectAttributes attrs) {
		attrs.addFlashAttribute("errorCode", "500");
		attrs.addFlashAttribute("errorMessage", "Unexpected Error: " + ex.getMessage());
		return new RedirectView("/error");
	}
}