package com.foxminded.school.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DataSource;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;

public class StudentService {
	 private static final String DB_PROPERTIES = "db.properties";
	 private StudentDao studentDao;
	 private DataSource dataSource;
	 private PropertyManager propertyManager = new PropertyManager();
	 private CourseDao courseDao;

	    public StudentService() {
	    	try {
				dataSource = propertyManager.getConnectionProperties(DB_PROPERTIES);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		this.studentDao = new StudentDao(dataSource);
		this.courseDao = new CourseDao(dataSource);
	    }

	    public void addNewStudent(String firstName, String lastName) {
	    	Student newStudent = new Student();
	        newStudent.setGroupId(new Random().nextInt(10) + 1);
	        newStudent.setFirstName(firstName);
	        newStudent.setLastName(lastName);
		    try {
			studentDao.insert(newStudent);
		    } catch (DAOException e) {
			e.printStackTrace();
		    };
	    }

	    public void deleteStudentByID(String studentID) {
	    	int id = Integer.parseInt(studentID);
		try {
		    studentDao.deleteById(id);
		} catch (DAOException e) {
		    e.printStackTrace();
		}
	    }
	    
	    public List<Student> getByCourseName(String name) {
	    	List<Student> students = new LinkedList<Student>();
			try {
				students = studentDao.getByCourseName(name);
			} catch (DAOException e) {
				e.printStackTrace();
			}
	    	return students;
	    }


	    public void deleteFromCourse(String sId, String cId) throws DAOException {
	    	int studentId = Integer.parseInt(sId);
	    	int courseId = Integer.parseInt(cId);
	        studentDao.deleteFromCourse(studentId, courseId);
	    }

	    public void assignToCourse(Integer studentId, Integer courseId) {
		try {
		    studentDao.assignToCourse(studentId, courseId);
		} catch (DAOException e) {
		    e.printStackTrace();
		}
	    }
	    
	    public List<Course> getCoursesByStudentId(String studentId) {
	    	int id = Integer.parseInt(studentId);
			List<Course> courses = new ArrayList<>();

			try {
			    courses = courseDao.getByStudentId(id);
			} catch (DAOException e) {
			    e.printStackTrace();
			}

			return courses;
		    }
	    
	    public List<Student> getAllStudents () {
	    	List<Student> answer = new LinkedList<>();
			try {
				answer = studentDao.getAll();
			} catch (DAOException e) {
				e.printStackTrace();
			}
	    	return answer;
	    }

}
