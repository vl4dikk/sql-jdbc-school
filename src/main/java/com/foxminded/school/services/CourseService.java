package com.foxminded.school.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DataSource;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Course;

public class CourseService {
	    private static final String DB_PROPERTIES = "db.properties";
		private CourseDao courseDao;
	    private DataSource dataSource;
	    private PropertyManager propertyManager = new PropertyManager();

	    public CourseService() {
	    	try {
				this.dataSource = propertyManager.getConnectionProperties(DB_PROPERTIES);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				this.courseDao = new CourseDao(dataSource);
			}
	    }

	    public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();

		try {
		    courses = courseDao.getAll();
		} catch (DAOException e) {
		    e.printStackTrace();
		}

		return courses;
	    }

	    public void insert(List<Course> courses) {
			try {
				courseDao.insert(courses);
			} catch (DAOException e) {
				e.printStackTrace();
			}
	    }

	    public List<String> getByStudentId(Integer studentId) {
		List<String> idCourses = new ArrayList<>();

		try {
		    idCourses = courseDao.getByStudentId(studentId).stream()
			    .map(course -> String.valueOf(course)).collect(Collectors.toList());
		} catch (DAOException e) {
		    e.printStackTrace();
		}

		return idCourses;
	    }
}
