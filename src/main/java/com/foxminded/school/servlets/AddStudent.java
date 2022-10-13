package com.foxminded.school.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.school.formatters.TableFormatter;

@WebServlet("/add-student")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TableFormatter tableFormatter;
	public void init(ServletConfig config) throws ServletException {
		tableFormatter = new TableFormatter();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String tableResponse = tableFormatter.addNewStudent(request.getParameter("firstName"), request.getParameter("lastName"));
	       PrintWriter out = response.getWriter();  
	       response.setContentType("text/html");  
	       out.println("<html><body>");    
	       out.println(tableResponse);   
	       out.println("</table>");  
	       out.println("</html></body>");  
	}

}
