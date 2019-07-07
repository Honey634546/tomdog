package com.honey.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter {
	public void init(FilterConfig fConfig);
	public void destroy();
//	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)throws IOException;
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain);
}
