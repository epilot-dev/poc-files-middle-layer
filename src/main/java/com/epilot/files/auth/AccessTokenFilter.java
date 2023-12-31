package com.epilot.files.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private Environment env;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		String method = request.getMethod();
		return path.equals("/") || method.equals("OPTIONS");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
      System.out.println(jwtToken);
		} else {
			System.out.println("JWT Token does not begin with Bearer String");
		}
    
		String jwksUrl = env.getProperty("jwks.url");
    RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(jwksUrl);
    Algorithm algorithm = Algorithm.RSA256(keyProvider);
    
    JWTVerifier jwtVerifier = JWT.require(algorithm).build();

		try {
			DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
			System.out.println("JWT Token verified: " + decodedJWT.getClaims());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		chain.doFilter(request, response);
	}
}
