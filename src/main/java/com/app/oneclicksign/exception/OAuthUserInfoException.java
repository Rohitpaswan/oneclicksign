package com.app.oneclicksign.exception;

public class OAuthUserInfoException extends RuntimeException {
	
	public OAuthUserInfoException(String message) {
		super(message);
	}
	
	public OAuthUserInfoException(String message, Throwable cause) {
		super(message, cause);
	}
}

