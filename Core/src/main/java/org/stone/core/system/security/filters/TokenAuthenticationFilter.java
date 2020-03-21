package org.stone.core.system.security.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.stone.Helper.jjwt.JWTTokenHelper;
import org.stone.core.system.token.JJWTTokenProvider;
import org.stone.core.system.token.TokenProvider;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
	private TokenProvider tokenProvider = null;

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	public TokenAuthenticationFilter(
			AuthenticationManager authenticationManager,
			TokenProvider tokenProvider) {
		super(authenticationManager);
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String tokenHeader = request.getHeader(JWTTokenHelper.TOKENHEADER);
		// 请求头中没有Authorization,则直接放行
		if (tokenHeader == null) {
			chain.doFilter(request, response);
			return;
		}
		// 请求头中有token，则进行解析并设置认证信息
		SecurityContextHolder.getContext().setAuthentication(
				getAuthentication(tokenHeader));
		super.doFilterInternal(request, response, chain);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(
			String tokenHeader) {
		String token = tokenHeader.replace(JWTTokenHelper.TOKENPREFIX, "");
		if (tokenProvider == null) {
			tokenProvider = new JJWTTokenProvider();
		}
		String username = tokenProvider.getUsername(token);
		if (username != null) {
			return new UsernamePasswordAuthenticationToken(username, null,
					new ArrayList<>());
		}
		return null;
	}
}
