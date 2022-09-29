package com.foxminded.school.data;

import java.util.List;
import java.util.Map;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DataSource;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;
import com.foxminded.school.models.Group;

public class DataGenerator {

	public static void generateTestData(Data data, DataSource dataSource) throws DAOException {
		try {
			List<Group> groups = data.getGroups();
			GroupDao groupDao = new GroupDao(dataSource);
			if (groupDao.getAll().size() < 2) {
				groupDao.insert(groups);
			}
			List<Course> courses = data.getCourses();
			CourseDao courseDao = new CourseDao(dataSource);
			if (courseDao.getAll().size() < 1) {
				courseDao.insert(courses);
			}
			List<Student> students = data.getStudents(groups);
			Map<Student, List<Course>> studentCourses = data.getStudentsCourses(students, courses);
			StudentDao studentDao = new StudentDao(dataSource);
			if (studentDao.getAll().size() < 2) {
				studentDao.insert(students);
				studentDao.assignToCourses(studentCourses);
			}		
		} catch (DAOException ex) {
			throw new DAOException("Cannot add data to database", ex);
		}
	}
	
	
}
