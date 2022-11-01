package com.foxminded.school.controllers;

import java.util.LinkedList;
import java.util.List;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;
import com.foxminded.school.services.StudentService;

public class StudentController {

	private StudentService service = new StudentService();

	public LinkedList<String> addNewStudent(String firstName, String lastName) {
		service.addNewStudent(firstName, lastName);
		LinkedList<String> answer = new LinkedList<String>();
		answer.add("<html><body>");
		answer.add("<h2>" + "Student " + firstName + " " + lastName + " inserted" + "</h2>");
		answer.add("</table>");
		answer.add("</html></body>");
		return answer;
	}

	public LinkedList<String> deleteStudentById(String id) {
		service.deleteStudentByID(id);
		LinkedList<String> answer = new LinkedList<String>();
		answer.add("<html><body>");
		answer.add("<h2>" + "Student has been deleted" + "</h2>");
		answer.add("</table>");
		answer.add("</html></body>");
		return answer;
	}

	public LinkedList<String> findStudentsByCourseName(String courseName) {
		LinkedList<String> answer = new LinkedList<>();
		List<Student> students = service.getByCourseName(courseName);

		answer.add("<html><body>");
		answer.add("<table border=1 width=50% height=50%>");
		answer.add("<tr><th>Students from course " + courseName + ":</th><tr>");

		for (Student student : students) {
			String name = student.getFirstName() + " " + student.getLastName();
			answer.add("<tr><td>" + name + "</td><tr>");
		}

		answer.add("</table>");
		answer.add("</html></body>");
		return answer;

	}

	public LinkedList<String> getCoursesByStudentId(String studentId) {
		LinkedList<String> answer = new LinkedList<>();
		List<Course> courses = service.getCoursesByStudentId(studentId);

		answer.add("<html><body>");
		answer.add("<table border=1 width=50% height=50%>");
		answer.add("<tr><th>Course id</th><th>Course name</th><th>Course description</th><tr>");

		for (Course course : courses) {
			String id = String.valueOf(course.getId());
			String name = course.getName();
			String description = course.getDescription();
			answer.add("<tr><td>" + id + "</td><td>" + name + "</td><td>" + description + "</td></tr>");
		}

		answer.add("</table>");
		answer.add("</html></body>");

		return answer;
	}
	
	public LinkedList<String> getAllStudents(){
		List<Student> students = service.getAllStudents();
		LinkedList<String> answer = new LinkedList<>();
		answer.add("<html><body>");
		answer.add("<table border=1 width=50% height=50%>");
		answer.add("<tr><th>Student id</th><th>Student name</th><tr>");
		for(Student student : students) {
			String id = String.valueOf(student.getId());
			String name = student.getFirstName() + " " + student.getLastName();
			answer.add("<tr><td>" + id + "</td><td>" + name + "</td></tr>");
		}
		
		answer.add("</table>");
		answer.add("</html></body>");
		
		return answer;
	}
	
	public LinkedList<String> deleteFromCourse(String studentId, String courseId){
		
		try {
			service.deleteFromCourse(studentId, courseId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		LinkedList<String> answer = new LinkedList<String>();
		answer.add("<html><body>");
		answer.add("<h2>" + "Student has been deleted from course with id :" + courseId + "</h2>");
		answer.add("</table>");
		answer.add("</html></body>");
		return answer;
	}

}
