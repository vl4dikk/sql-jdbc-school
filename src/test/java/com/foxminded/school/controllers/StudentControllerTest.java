package com.foxminded.school.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;
import com.foxminded.school.services.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

	@Mock
	StudentService service = new StudentService();

	@InjectMocks
	StudentController controller = new StudentController();

	@Test
	void testAddNewStudent() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<h2>Student Vlad Valchuk inserted</h2>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<String> actual = controller.addNewStudent("Vlad", "Valchuk");
		Mockito.verify(service , times(1)).addNewStudent(anyString(), anyString());
		assertEquals(expected, actual);
		
	}

	@Test
	void testDeleteStudentById() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<h2>Student has been deleted</h2>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<String> actual = controller.deleteStudentById("1");
		Mockito.verify(service, times(1)).deleteStudentByID(anyString());
		assertEquals(expected, actual);
	}

	@Test
	void testFindStudentsByCourseName() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<table border=1 width=50% height=50%>");
		expected.add("<tr><th>Students from course 123:</th><tr>");
		expected.add("<tr><td>firstName1 lastName1</td><tr>");
		expected.add("<tr><td>firstName2 lastName2</td><tr>");
		expected.add("<tr><td>firstName3 lastName3</td><tr>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<Student> students = new LinkedList<>();
		students.add(new Student(1, 2, "firstName1", "lastName1"));
		students.add(new Student(2, 2, "firstName2", "lastName2"));
		students.add(new Student(3, 2, "firstName3", "lastName3"));
		Mockito.when(service.getByCourseName(anyString())).thenReturn(students);
		List<String> actual = controller.findStudentsByCourseName("123");
		Mockito.verify(service, times(1)).getByCourseName(anyString());
		assertEquals(expected, actual);
	}

	@Test
	void testGetCoursesByStudentId() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<table border=1 width=50% height=50%>");
		expected.add("<tr><th>Course id</th><th>Course name</th><th>Course description</th><tr>");
		expected.add("<tr><td>1</td><td>name1</td><td>description1</td></tr>");
		expected.add("<tr><td>2</td><td>name2</td><td>description2</td></tr>");
		expected.add("<tr><td>3</td><td>name3</td><td>description3</td></tr>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<Course> courses = new LinkedList<>();
		courses.add(new Course(1, "name1", "description1"));
		courses.add(new Course(2, "name2", "description2"));
		courses.add(new Course(3, "name3", "description3"));
		Mockito.when(service.getCoursesByStudentId(anyString())).thenReturn(courses);
		List<String> actual = controller.getCoursesByStudentId("2");
		Mockito.verify(service, times(1)).getCoursesByStudentId(anyString());
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStudents() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<table border=1 width=50% height=50%>");
		expected.add("<tr><th>Student id</th><th>Student name</th><tr>");
		expected.add("<tr><td>1</td><td>firstName1 lastName1</td></tr>");
		expected.add("<tr><td>2</td><td>firstName2 lastName2</td></tr>");
		expected.add("<tr><td>3</td><td>firstName3 lastName3</td></tr>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<Student> students = new LinkedList<>();
		students.add(new Student(1, 2, "firstName1", "lastName1"));
		students.add(new Student(2, 2, "firstName2", "lastName2"));
		students.add(new Student(3, 2, "firstName3", "lastName3"));
		Mockito.when(service.getAllStudents()).thenReturn(students);
		List<String> actual = controller.getAllStudents();
		Mockito.verify(service, times(1)).getAllStudents();
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteFromCourse() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<h2>Student has been deleted from course with id :1</h2>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<String> actual = controller.deleteFromCourse("1", "1");
		try {
			Mockito.verify(service, times(1)).deleteFromCourse(anyString(), anyString());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllCourses() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<table border=1 width=50% height=50%>");
		expected.add("<tr><th>Course id</th><th>Course name</th><th>Course description</th><tr>");
		expected.add("<tr><td>1</td><td>name1</td><td>description1</td></tr>");
		expected.add("<tr><td>2</td><td>name2</td><td>description2</td></tr>");
		expected.add("<tr><td>3</td><td>name3</td><td>description3</td></tr>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<Course> courses = new LinkedList<>();
		courses.add(new Course(1, "name1", "description1"));
		courses.add(new Course(2, "name2", "description2"));
		courses.add(new Course(3, "name3", "description3"));
		Mockito.when(service.getAllCourses()).thenReturn(courses);
		List<String> actual = controller.getAllCourses();
		Mockito.verify(service, times(1)).getAllCourses();
		assertEquals(expected, actual);
	}

	@Test
	void testAssignToCourse() {
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<h2>Student, with id :1was added to course with id :1</h2>");
		expected.add("</table>");
		expected.add("</html></body>");
		List<String> actual = controller.assignToCourse("1", "1");
		Mockito.verify(service, times(1)).assignToCourse(anyString(), anyString());
		assertEquals(expected, actual);
	}

}
