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

@WebServlet("/find-all-groups")
public class FindAllGroups extends HttpServlet {
	private static final long serialVersionUID = 4083592750278620437L;
	private TableFormatter tableFormatter;
	public void init(ServletConfig config) throws ServletException {
		tableFormatter = new TableFormatter();
	}
       
	
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
    {  
       List<String> table = tableFormatter.findAllGroups(Integer.parseInt(req.getParameter("count")));
       PrintWriter out = res.getWriter();  
       res.setContentType("text/html");  
       out.println("<html><body>");    
           out.println("<table border=1 width=50% height=50%>");  
           out.println("<tr><th>Group name</th><th>Students</th><tr>");  
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
