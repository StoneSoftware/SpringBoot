package org.stone.Helper.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTTokenHelper {

	public static final String TOKENHEADER = "Authorization";

	public static final String TOKENPREFIX = "Bearer ";

	private static final String SECRET = "wu_firefox@163.com";

	private static final String ISS = "wu_firefox@163.com";

	// 过期时间3600秒
	private static final long EXPIRATION = 3600L;

	// 过期时间7天
	private static final long EXPIRATION_REMEMBER = 604800L;

	// 创建token
	public static String createToken(String username, boolean isRememberMe) {
		long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
		return Jwts
				.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.setIssuer(ISS)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(
						new Date(System.currentTimeMillis() + expiration * 1000))
				.compact();
	}

	public static String getUsername(String token) {
		return getTokenBody(token).getSubject();
	}

	// 是否过期
	public static boolean isExpiration(String token) {
		return getTokenBody(token).getExpiration().before(new Date());
	}

	private static Claims getTokenBody(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
				.getBody();
	}
}
