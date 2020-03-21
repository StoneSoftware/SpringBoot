package org.stone.core.system.security.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.stone.Helper.jjwt.JWTTokenHelper;
import org.stone.core.system.security.vo.User;
import org.stone.core.system.security.vo.UserDetailsImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(String defaultFilterProcessesUrl,
			AuthenticationManager authenticationManager) {
		setFilterProcessesUrl(defaultFilterProcessesUrl);
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		// 从request中获取登录信息
		try {
			User loginUser = new ObjectMapper().readValue(
					request.getInputStream(), User.class);
			UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(
					loginUser.getUsername(), loginUser.getPassword(),
					new ArrayList<>());
			Authentication auth = authenticationManager
					.authenticate(usernamePasswordToken);
			return auth;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 成功验证回调方法，验证成功，就生成token并返回
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetailsImpl jwtUser = (UserDetailsImpl) authResult.getPrincipal();
		String token = JWTTokenHelper.createToken(jwtUser.getUsername(), false);
		response.setHeader("token", JWTTokenHelper.TOKENPREFIX + token);
	}

	// 失败验证回调方法
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		response.getWriter()
				.write("authentication failed, the reason is-> "
						+ failed.getMessage());
		throw failed;
	}
}