package com.honey.servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
public class test4_jsp extends HttpServlet { 
public void doGet(HttpServletRequest request, HttpServletResponse response ) { 
PrintWriter out=response.getWriter();
response.println(" <meta http-equiv=Content-Type content=\"text/html;charset=utf-8\"><html> 此工作属于提高要�?<body bgcolor=\"white\"> <h1>The Echo JSP - Testing for Jsp tasks</h1> ");
   java.util.Enumeration eh = request.getHeaderNames(); 
     while (eh.hasMoreElements()) { 
         String h = (String) eh.nextElement(); 
         out.print("<br> header: " + h ); 
         out.println(" value: " + request.getHeader(h)); 
     } 

response.println(" </body> </html> ");
}
}