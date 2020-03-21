package org.stone.core.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.stone.core.system.security.exception.SystemAuthenticationEntryPoint;
import org.stone.core.system.security.filters.JWTAuthenticationFilter;
import org.stone.core.system.security.filters.TokenAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configurable
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Autowired
	private SystemAuthenticationEntryPoint systemAuthenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",
				new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(systemAuthenticationEntryPoint)
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				// 需要验证才能访问
				.antMatchers("/**")
				.authenticated()
				// 其他都放行了
				.anyRequest()
				.permitAll()
				.and()
				.addFilterBefore(
						new JWTAuthenticationFilter("/test/login",
								authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilter(
						new TokenAuthenticationFilter(authenticationManager()))
				// 禁用session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public HttpFirewall httpFirewall() {
		return new DefaultHttpFirewall();
	}

}