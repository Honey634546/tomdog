package com.honey.servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
public class show5_jsp extends HttpServlet { 
public void doGet(HttpServletRequest request, HttpServletResponse response ) { 
PrintWriter out=response.getWriter();
response.println("<!DOCTYPE html><html><head><title>Testing for Filter</title><body> <h1>Testing for Filter</h1> <p>The site have been visited ");
response.println(javax.servlet.http.AccessFilter.nNum);
response.println(" times.<p></body> </html>");
}
}