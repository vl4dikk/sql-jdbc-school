package com.foxminded.school.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;

public class StudentService {
	 private StudentDao studentDao;
	 private CourseDao courseDao;

	    public StudentService() {
		this.studentDao = new StudentDao();
		this.courseDao = new CourseDao();
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

	    public void assignToCourse(String sId, String cId) {
	    int studentId = Integer.parseInt(sId);
	    int courseId = Integer.parseInt(cId);
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
	    
	    public List<Course> getAllCourses(){
	    	List<Course> answer = new LinkedList<>();
	    	try {
				answer = courseDao.getAll();
			} catch (DAOException e) {
				e.printStackTrace();
			}
	    	return answer;
	    }

}
