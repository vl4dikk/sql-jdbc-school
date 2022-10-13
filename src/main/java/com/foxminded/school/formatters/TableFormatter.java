package com.foxminded.school.formatters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DataSource;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Group;
import com.foxminded.school.models.Student;

public class TableFormatter {

	private static final String DB_PROPERTIES = "db.properties";
	private  DataSource dataSource;
    private  CourseDao courseDao;
    private  GroupDao groupDao;
    private  StudentDao studentDao;

	public TableFormatter() {
		try {
			dataSource = PropertyManager.getConnectionProperties(DB_PROPERTIES);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		courseDao = new CourseDao(dataSource);
		groupDao = new GroupDao(dataSource);
		studentDao = new StudentDao(dataSource);
	}
	
	public List<String> findAllGroups (int i) {
		LinkedList<String> tableOfGroups = new LinkedList<>();
		List<Group> groups = new LinkedList<Group>();
		try {
			groups = groupDao.getByStudentsCount(i);
		} catch (DAOException ex) {
			 System.out.println("Cannot find groups\n" + ex.getMessage());
		}
		for (Group group : groups) {
			String name = group.getName();
			String count = String.valueOf(group.getStudentsCount());
			tableOfGroups.add("<tr><td>" + name + "</td><td>" + count + "</td></tr>");
		}
		return tableOfGroups;
	}
	
	 public List<String> findStudentsByCourseName(String courseName) {
		 LinkedList<String> listOfStudents = new LinkedList<>();
		 List<Student> students = new LinkedList<Student>();
			try {
				students = studentDao.getByCourseName(courseName);
			} catch (DAOException ex) {
				 System.out.println("Cannot find groups\n" + ex.getMessage());
			}
			for (Student student : students) {
				String name = student.getFirstName() + " " + student.getLastName();
				listOfStudents.add("<tr><td>" + name + "</td><tr>");
			}
			return listOfStudents;
		 
	 }
	 
	 public String addNewStudent (String firstName, String lastName) {
		    String answer;
	        Student newStudent = new Student();
	        newStudent.setGroupId(new Random().nextInt(10) + 1);
	        newStudent.setFirstName(firstName);
	        newStudent.setLastName(lastName);
	        try {
	            studentDao.insert(newStudent);
	        } catch (DAOException ex) {
	            System.out.println("Cannot add student\n" + ex.getMessage());
	            answer = "<h2>" + "Cannot add student" + "</h2>";
	        }
	        System.out.println("Student " + newStudent.getFirstName() + " " + newStudent.getLastName() + " inserted");
	        answer = "<h2>" +  "Student " + newStudent.getFirstName() + " " + newStudent.getLastName() + " inserted" + "</h2>";
	        return answer;
	 }
	 
	 public String deleteStudentById (int id) {
		    String answer;
	        try {
	            studentDao.deleteById(id);
	        } catch (DAOException ex) {
	            System.out.println("Cannot delete student\n" + ex.getMessage());
	            answer = "<h2>" + "Cannot delete student" + "</h2>";
	        }
	        System.out.println("Student has been deleted");
	        answer = "<h2>" +  "Student has been deleted" + "</h2>";
	        return answer;
	 }

}
