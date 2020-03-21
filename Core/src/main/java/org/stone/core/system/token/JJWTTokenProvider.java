package org.stone.core.system.token;

import org.stone.Helper.jjwt.JWTTokenHelper;

public class JJWTTokenProvider implements TokenProvider {

	@Override
	public String createToken(String username, boolean isRememberMe) {
		return JWTTokenHelper.createToken(username, isRememberMe);
	}

	@Override
	public String getUsername(String token) {
		return JWTTokenHelper.getUsername(token);
	}

	@Override
	public boolean isExpiration(String token) {
		return JWTTokenHelper.isExpiration(token);
	}

}
