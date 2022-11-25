package com.foxminded.school.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DataSource;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Group;
import com.foxminded.school.models.Student;

class DataGeneratorTest {
	private DataSource dataSource = new DataSource("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", "sa", "sa");
	private DataGenerator generator = new DataGenerator();
	private CourseDao courseDao = new CourseDao();
	private GroupDao groupDao = new GroupDao();
	private StudentDao studentDao = new StudentDao();

	@BeforeEach
	private void setDataSource() {
		PropertyManager propertyManager = new PropertyManager();
		String createTables = "src/test/resources/create_tables.sql";
		try {
			propertyManager.createTablesInDatabase(dataSource, createTables);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGenerateTestData() {
		List<Course> courses = new LinkedList<>();
		List<Group> groups = new LinkedList<>();
		List<Student> students = new LinkedList<>();
		int coursesCount = 10;
		int groupsCount = 10;
		int studentsCount = 200;
		try {
			generator.generateTestData();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			courses = courseDao.getAll();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			groups = groupDao.getAll();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		try {
			students = studentDao.getAll();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertEquals(coursesCount, courses.size());
		assertEquals(groupsCount, groups.size());
		assertEquals(studentsCount, students.size());
	}

}
