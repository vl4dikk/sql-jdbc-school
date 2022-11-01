package com.foxminded.school.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.school.controllers.GroupController;

public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 3703731768382677084L;
	
	private String contextType;
	private GroupController controller = new GroupController();
	
	public void init(ServletConfig config) throws ServletException {
		 this.contextType = "text/html";
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		 response.setContentType(contextType);  
	     PrintWriter out = response.getWriter(); 
	     
	     out.print("<form method = post>");  
	     out.print("<br> <label>Find all groups with less or"
	     		+ "			equals student count:</label><br>");  
	     out.print("<br> <input type=\"text\" name=\"count\"> <input"
	     		+ "			type=\"submit\" value=\"Search\">"); 
	        
	     out.print("</form>");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		        PrintWriter out = res.getWriter();  
			    String count = req.getParameter("count");
			    res.setContentType(contextType);
			    LinkedList<String> response = controller.findAllGroups(count);
			    
			    for(String answer : response) {
			    	out.print(answer);
			    }
			    
			}

}
