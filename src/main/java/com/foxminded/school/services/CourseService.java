package com.foxminded.school.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
			idCourses = courseDao.getByStudentId(studentId).stream().map(course -> String.valueOf(course))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return idCourses;
	}
}
