package com.foxminded.school.services;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;

public class CourseService {
	private CourseDao courseDao;

	public CourseService() {
		this.courseDao = new CourseDao();
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
}
