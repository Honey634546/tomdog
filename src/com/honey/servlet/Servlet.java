package com.honey.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Honey
 *
 */
public interface Servlet {
	public void service(HttpServletRequest req,HttpServletResponse resp);
	public void doGet(HttpServletRequest req,HttpServletResponse resp);
	public void doPost(HttpServletRequest req,HttpServletResponse resp);
}
