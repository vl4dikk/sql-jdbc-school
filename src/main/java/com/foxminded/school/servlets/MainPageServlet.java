package com.foxminded.school.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.school.dao.DataSource;
import com.foxminded.school.data.DataGenerator;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PathManager;
import com.foxminded.school.managers.PropertyManager;

public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = -5380455625030331924L;

	private String mymsg;
	private static final String DB_PROPERTIES = "db.properties";
	private static final String CREATE_TABLES_SCRIPT = "create_tables.sql";

	public void init(ServletConfig config) throws ServletException {
		mymsg = "Choose option:";
		try {
			String createTablesScript = PathManager.getFilePath(CREATE_TABLES_SCRIPT);
			try {
				DataSource dataSource = PropertyManager.getConnectionProperties(DB_PROPERTIES);
				PropertyManager.createTablesInDatabase(dataSource, createTablesScript);

				DataGenerator generator = new DataGenerator();
				generator.generateTestData(dataSource);
			} catch (DAOException ex) {
				System.out.println("Connection failed...\n" + ex);
			} catch (ClassNotFoundException ex) {
				System.out.println("Cannot load Database Driver\n" + ex);
			}
		} catch (IOException ex) {
			System.out.println("File not found!\n" + ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<h3>" + mymsg + "</h3>");
		out.println("<a href=\"pages/FindAllGroups.jsp\">Find all groups with less or equals student count</a></div><br>");
		out.println(
				"<a href=\"pages/FindAllStudents.jsp\">Find all students related to course with given name</a></div><br>");
		out.println("<a href=\"pages/AddNewStudent.jsp\">Add new student</a></div><br>");
		out.println("<a href=\"pages/DeleteStudent.jsp\">Delete student by STUDENT_ID</a></div><br>");
		out.println("<a href=\"pages/AddStudentToCourse.jsp\">Add a student to the course (from a list)</a></div><br>");
		out.println("<a href=\"pages/RemoveStudentFromCourse.jsp\">Remove the student from one of his or her courses</a></div><br>");
	}

	public void destroy() {
	}

}
