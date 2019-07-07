package com.honey.servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
public class test2_jsp extends HttpServlet { 
public void doGet(HttpServletRequest request, HttpServletResponse response ) { 
PrintWriter out=response.getWriter();
response.println("<b>Testing for first JSP</b><br><b> current time is:     ");
response.println(  new java.util.Date() );
response.println(" </b> ");
}
}