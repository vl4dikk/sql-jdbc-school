package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Course;

@ExtendWith(MockitoExtension.class)
class CourseDaoTest {

	private DataSource dataSource = new DataSource("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", "sa", "sa");

	@Mock
	private PropertyManager propertyManagerMock = new PropertyManager();

	@InjectMocks
	private CourseDao courseDao = new CourseDao();

	@BeforeEach
	private void setDataSource() {
		PropertyManager propertyManager = new PropertyManager();
		String createTables = "src/test/resources/create_tables.sql";
		try {
			Mockito.lenient().when(propertyManagerMock.getConnectionProperties(anyString())).thenReturn(dataSource);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			propertyManager.createTablesInDatabase(dataSource, createTables);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void insert_ShouldThrowException_WhenGivenNull() {
		assertThrows(IllegalArgumentException.class, () -> courseDao.insert(null));
	}

	@Test
	void insert_ShouldAddCoursesWithCorrectId_WhenGivenCoursesWithDuplicatedId() throws DAOException {
		List<Course> duplicatedId = Arrays.asList(new Course(1, "Math", "This is mathematics"),
				new Course(1, "History", "This is History"));
		courseDao.insert(duplicatedId);
		List<Course> actual = courseDao.getAll();
		duplicatedId.get(1).setId(2);
		assertEquals(duplicatedId, actual);
	}

	@Test
	void insert_ShouldAddToDatabaseCourses_WhenGivenCourses() throws DAOException {
		List<Course> expected = Arrays.asList(new Course(1, "Math", "This is mathematics"),
				new Course(2, "History", "This is History"));
		courseDao.insert(expected);
		List<Course> actual = courseDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void getAll_ShouldReturnEmptyList_WhenTableIsEmpty() throws DAOException {
		List<Course> expected = new ArrayList<>();
		List<Course> actual = courseDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void getAll_ShouldReturnAllCourses_WhenThereAreFewCourses() throws DAOException {
		List<Course> expected = Arrays.asList(new Course(1, "Math", "This is mathematics"),
				new Course(2, "History", "This is History"), new Course(3, "Geography", "This is Geography"));
		courseDao.insert(expected);
		List<Course> actual = courseDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void getById_ShouldReturnEmptyList_WhenTableDoNotContainPassedId() throws DAOException {
		List<Course> expected = new ArrayList<>();
		List<Course> actual = courseDao.getByStudentId(0);
		assertEquals(expected, actual);
	}

}
