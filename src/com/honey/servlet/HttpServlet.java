package com.honey.servlet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Honey
 *
 */

abstract public class HttpServlet implements Servlet{
	public void service(HttpServletRequest req,HttpServletResponse resp) {
		if(req.getMethod().equals("get"))
			doGet(req, resp);
		else if(req.getMethod().equals("post"))
			doPost(req, resp);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse resp) {
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp){
	}

}
