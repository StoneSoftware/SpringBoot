package org.stone.App.aggregation.security.po;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = -165753705037514610L;

	private String username;

	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl() {
	}

	public UserDetailsImpl(User user) {
		username = user.getUsername();
		password = user.getPassword();
		authorities = Collections.singleton(new SimpleGrantedAuthority(user
				.getRole()));
	}

	// 获取用户的授权信息
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// 获取用户密码(凭证)
	@Override
	public String getPassword() {
		return password;
	}

	// 获取用户名
	@Override
	public String getUsername() {
		return username;
	}

	// // 判断帐号是否已经过期 ，默认是false
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 判断帐号是否已被锁定，默认是false
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 判断用户凭证是否已经过期 ，默认是false
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 判断用户帐号是否已启用 ，默认false
	@Override
	public boolean isEnabled() {
		return true;
	}

}
