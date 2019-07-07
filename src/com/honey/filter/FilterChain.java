package com.honey.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.honey.servlet.Servlet;

public class FilterChain {
	private Servlet servlet;
	private int index = 0;
	private List<Filter> filterList = new ArrayList<>();
	
	public void doFilter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(index < filterList.size()) {
			filterList.get(index++).doFilter(req, resp, this);
		}
	}
	
	public void addFilter(Filter filter) {
		filterList.add(filter);
	}
	
	public void setServlet(Servlet servlet) {
		this.servlet = servlet;
	}
	
	public Servlet getServlet() {
		return servlet;
	}
}
