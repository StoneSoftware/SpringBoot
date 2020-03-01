package org.stone.App.aggregation.security.filters;

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

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String tokenHeader = request.getHeader(JWTTokenHelper.TOKENHEADER);
		// 请求头中没有Authorization,则直接放行
		if (tokenHeader == null
				|| tokenHeader.startsWith(JWTTokenHelper.TOKENPREFIX)) {
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
		String username = JWTTokenHelper.getUsername(token);
		if (username != null) {
			return new UsernamePasswordAuthenticationToken(username, null,
					new ArrayList<>());
		}
		return null;
	}
}