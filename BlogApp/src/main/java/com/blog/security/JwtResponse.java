package com.blog.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
	 private String jwtToken;
	 
}
