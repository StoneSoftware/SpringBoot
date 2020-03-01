package org.stone.App.aggregation.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@WebFilter(value = "/crossDomainFilter", urlPatterns = "/*")
public class CrossDomainFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"GET,POST,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Max-Age", "180");
		response.setHeader("Content-Type",
				"application/json,application/x-www-form-urlencoded");
		response.setHeader(
				"Access-Control-Allow-Headers",
				"User-Agent,Referer,Origin,Host,Connection,Access-Control-Request-Method,Access-Control-Request-Headers,Cache-Control,Origin,X-Requested-With,Content-Type,Accept,Accept-Encoding,Accept-Language,Authorization");
		response.setHeader("Access-Control-Expose-Headers", "token");
		chain.doFilter(req, res);
	}

}
