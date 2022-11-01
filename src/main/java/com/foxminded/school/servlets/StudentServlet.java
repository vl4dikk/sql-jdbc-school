package com.foxminded.school.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.school.controllers.StudentController;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentController controller = new StudentController();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		if (type.equals("FindAll")) {
			printFindAll(out);
		}
		else if (type.equals("AddStudent")) {
			printAddStudent(out);
		}
		else if (type.equals("DeleteStudent")) {
			printDeleteStudent(out);
		}
		else if (type.equals("DeleteFromCourse")) {
			printDeleteFromCourse(out);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (request.getParameter("course") != null) {
			String course = request.getParameter("course");
			LinkedList<String> answer = controller.findStudentsByCourseName(course);
			for(String res : answer) {
				out.println(res);
			}		
		}
		else if (request.getParameter("firstName") != null & request.getParameter("lastName") != null) {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			LinkedList<String> answer = controller.addNewStudent(firstName, lastName);
			for(String res : answer) {
				out.print(res);
			}
		}
		else if (request.getParameter("STUDENT_ID") != null) {
			String id = request.getParameter("STUDENT_ID");
			LinkedList<String> answer = controller.deleteStudentById(id);
			for(String res : answer) {
				out.print(res);
			}
		}
		else if (request.getParameter("STUDENT_ID_DELETE") != null) {
			printDeleteFromCourse2(out, request.getParameter("STUDENT_ID_DELETE"));
		}
//		else if (request.getParameter("COURSE_ID_DELETE") != null) {
//			LinkedList<String> answer = controller.deleteFromCourse(studentIdDelete, courseIdDelete);
//			for (String res : answer) {
//				out.print(res);
//			}
//		}
	}

	private void printFindAll(PrintWriter out) {
		out.print("<form method = post>");
		out.print("<br> <label for=\"GET-name\">Find all students related to\r\n"
				+ "			course with given name:</label><br>");
		out.print("<br> <input id=\"GET-name\" type=\"text\" name=\"course\"> <input\r\n"
				+ "			type=\"submit\" value=\"Search\">");
		out.print("</form>");
	}
	
	private void printAddStudent(PrintWriter out) {
		out.print("<form method = post>");
		out.print("<br> <label for=\"GET-name\">Add new student:</label><br>");
		out.print("<br> <input id=\"GET-name\" type=\"text\" name=\"firstName\" placeholder=\"FirstName\"> <br>");
		out.print("<input id=\"GET-name\" type=\"text\" name=\"lastName\" placeholder=\"LastName\"><br>");		
		out.print("		<input\r\n"
				+ "			type=\"submit\" value=\"Add\">");		
		out.print("</form>");
	}
	
	private void printDeleteStudent(PrintWriter out) {
		out.print("<form method = post>");
		out.print("<br> <label for=\"GET-name\">Delete student by STUDENT_ID:</label><br>");
		out.print("<br> <input id=\"GET-name\" type=\"text\" name=\"STUDENT_ID\">");
		out.print("<input\r\n"
				+ "			type=\"submit\" value=\"Delete\">");			
		out.print("</form>");
	}
	
	private void printDeleteFromCourse(PrintWriter out) {
		out.print("<form method = post>");
		out.print("<br> <label for=\"GET-name\">Enter student id :</label><br>");
		out.print("<br> <input id=\"GET-name\" type=\"text\" name=\"STUDENT_ID_DELETE\">");
		out.print("<input\r\n"
				+ "			type=\"submit\" value=\"Next\">");			
		out.print("</form>");
		
		LinkedList<String> answer = controller.getAllStudents();
		for (String res : answer) {
			out.print(res);
		}
	}
	
	private void printDeleteFromCourse2(PrintWriter out, String studentId) {
		out.print("<form method = post>");
		out.print("<br> <label for=\"GET-name\">Enter course id :</label><br>");
		out.print("<br> <input id=\"GET-name\" type=\"text\" name=\"COURSE_ID_DELETE\">");
		out.print("<input\r\n"
				+ "			type=\"submit\" value=\"Delete\">");			
		out.print("</form>");
		
		LinkedList<String> answer = controller.getCoursesByStudentId(studentId);
		for (String res : answer) {
			out.print(res);
		}
	}
}
