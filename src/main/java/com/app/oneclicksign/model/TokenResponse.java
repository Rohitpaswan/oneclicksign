package com.app.oneclicksign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class TokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
}
