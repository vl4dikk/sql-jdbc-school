package com.foxminded.school.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	StudentDao studentDao = new StudentDao();
	@Mock
	CourseDao courseDao = new CourseDao();
	
	@InjectMocks
	StudentService service = new StudentService();

	@Test
	void testAddNewStudent() {
		service.addNewStudent("Vlad", "Valchuk");
		try {
			verify(studentDao, times(1)).insert(any(Student.class));
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeleteStudentByID() {
		service.deleteStudentByID("13");
		
		try {
			verify(studentDao, times(1)).deleteById(anyInt());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetByCourseName() {
		List<Student> expected = new LinkedList<>();
		expected.add(new Student(1, 2, "Vlad", "Valchuk"));
		expected.add(new Student(3, 5, "Vlad", "Val"));
		expected.add(new Student(4, 2, "Vlad", "Chuk"));
		expected.add(new Student(6, 3, "Vlad", "Alchuk"));
		String name = "Course name";
		try {
			Mockito.when(studentDao.getByCourseName(name)).thenReturn(expected);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Student> actual = service.getByCourseName(name);
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteFromCourse() {
		try {
			service.deleteFromCourse("1", "3");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			verify(studentDao, times(1)).deleteFromCourse(anyInt(), anyInt());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void testAssignToCourse() {
		service.assignToCourse("1", "3");
		
		try {
			verify(studentDao, times(1)).assignToCourse(anyInt(), anyInt());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetCoursesByStudentId() {
		List<Course> expected = new LinkedList<>();
		expected.add(new Course(1, "name1", "description1"));
		expected.add(new Course(2, "name2", "description2"));
		expected.add(new Course(3, "name3", "description3"));
		expected.add(new Course(4, "name4", "description4"));
		String idForServiceMethod = "1";
		int idForDaoMethod = 1;
		try {
			Mockito.when(courseDao.getByStudentId(idForDaoMethod)).thenReturn(expected);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Course> actual = service.getCoursesByStudentId(idForServiceMethod);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStudents() {
		List<Student> expected = new LinkedList<>();
		expected.add(new Student(1, 2, "Vlad", "Valchuk"));
		expected.add(new Student(3, 5, "Vlad", "Val"));
		expected.add(new Student(4, 2, "Vlad", "Chuk"));
		expected.add(new Student(6, 3, "Vlad", "Alchuk"));
		try {
			Mockito.when(studentDao.getAll()).thenReturn(expected);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Student> actual = service.getAllStudents();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllCourses() {
		List<Course> expected = new LinkedList<>();
		expected.add(new Course(1, "name1", "description1"));
		expected.add(new Course(2, "name2", "description2"));
		expected.add(new Course(3, "name3", "description3"));
		expected.add(new Course(4, "name4", "description4"));
		try {
			Mockito.when(courseDao.getAll()).thenReturn(expected);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Course> actual = service.getAllCourses();
		assertEquals(expected, actual);
	}

}
