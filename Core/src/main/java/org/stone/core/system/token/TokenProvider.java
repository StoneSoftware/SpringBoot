package org.stone.core.system.token;

public interface TokenProvider {
	public String createToken(String username, boolean isRememberMe);

	public String getUsername(String token);

	public boolean isExpiration(String token);

}
