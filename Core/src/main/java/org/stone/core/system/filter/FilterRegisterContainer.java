package org.stone.core.system.filter;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.stone.core.system.filter.cors.CrossDomainFilter;

/**
 * <过滤器注册容器,用于向系统中注入各类过滤器>
 * 
 * @author Administrator
 *
 */
@Configurable
public class FilterRegisterContainer {
	@Bean
	public FilterRegistrationBean<Filter> filterRegist() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		registrationBean.setFilter(new CrossDomainFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

}
