package com.royshruti.q16;

import java.io.*;
import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/q16/hello")
public class HelloServlet extends HttpServlet {
	@Override
	public void init() {
		System.out.println("hello from init");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Hello World Servlet</title></head>");
		out.println("<body>");
		out.println("<h1>Hello World! testing</h1>");
		out.println("</body></html>");
		out.close();
	}
}

/*
 * 
 * //compile and put the class file in classes folder in web inf
 * 
 * //add to web-inf..web.xml
 * <servlet>
 * <servlet-name>HelloWorld</servlet-name>
 * <servlet-class>HelloWorldServlet</servlet-class>
 * </servlet>
 * 
 * <servlet-mapping>
 * <servlet-name>HelloWorld</servlet-name>
 * <url-pattern>/servlet/HelloWorld</url-pattern>
 * </servlet-mapping>
 * 
 * 
 */