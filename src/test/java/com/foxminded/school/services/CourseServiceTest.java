package com.foxminded.school.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
	
	@Mock
	private CourseDao courseDao;
	
	@InjectMocks
	private CourseService service = new CourseService();

	@Test
	void testGetAllCourses() {
		List<Course> courses = new LinkedList<>();
		courses.add(new Course(1, "name1", "123"));
		courses.add(new Course(2, "name2", "321"));
		courses.add(new Course(3, "name3", "456"));
		try {
			Mockito.when(courseDao.getAll()).thenReturn(courses);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Course> actual = service.getAllCourses();
		assertEquals(courses, actual);
	}
}
