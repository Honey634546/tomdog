package com.honey.servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
public class show3_jsp extends HttpServlet { 
public void doPost(HttpServletRequest request, HttpServletResponse response ) { 
PrintWriter out=response.getWriter();
response.println("<!DOCTYPE html><html><head><title>Testing for Servlet-MVC</title><body> <h1>Recommended Pet - Testing for Web-MVC</h1> <p>You want a ");
response.println(request.getParameter("legs"));
response.println("-legged pet weighing ");
response.println(request.getParameter("weight"));
response.println("lbs.</p><p> We recommend getting <b>");
response.println(request.getAttribute("pet"));
response.println("</b></p></body> </html>");
}
}