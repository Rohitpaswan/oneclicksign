package com.app.oneclicksign.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google")
@Data
public class GoogleConfig {
	private String clientId;
	private String clientSecret;
	private String authUri;
	private String tokenUri;
	private String userinfoUri;
	private String redirectUri;
	private String scope;
}
