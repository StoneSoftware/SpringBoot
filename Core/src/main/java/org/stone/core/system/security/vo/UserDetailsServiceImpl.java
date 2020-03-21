package org.stone.core.system.security.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * <模拟数据库操作业务>
	 */
	@Override
	public UserDetails loadUserByUsername(String s)
			throws UsernameNotFoundException {
		String password = "123456";
		User user = new User();
		user.setUsername("root");
		user.setPassword(encoder.encode(password));
		return new UserDetailsImpl(user);
	}

}
