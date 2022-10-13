package com.foxminded.school.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.school.formatters.TableFormatter;

/**
 * Servlet implementation class FindAllStudents
 */
@WebServlet("/find-all-students")
public class FindAllStudents extends HttpServlet {
	private static final long serialVersionUID = 5491782136408674460L;
	private TableFormatter tableFormatter;

	public void init(ServletConfig config) throws ServletException {
		tableFormatter = new TableFormatter();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> table = tableFormatter.findStudentsByCourseName(request.getParameter("name"));
	       PrintWriter out = response.getWriter();  
	       response.setContentType("text/html");  
	       out.println("<html><body>");    
	           out.println("<table border=1 width=50% height=50%>");  
	           out.println("<tr><th>\"Students from course" + request.getParameter("name") +":</th><tr>");  
	           for (String string : table) 
	           {    
	               out.println(string);   
	           }  
	           out.println("</table>");  
	           out.println("</html></body>");  
	}
	
	public void destroy() {
	}

}
