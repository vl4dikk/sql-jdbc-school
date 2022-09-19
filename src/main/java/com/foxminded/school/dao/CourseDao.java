package com.foxminded.school.dao;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.dao.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseDao {

	private static final String GET_ALL_COURSES_QUERY = "SELECT * FROM courses";
	private static final String INSERT_COURSE_QUERY = "INSERT INTO courses (course_name, course_description) "
			+ "     VALUES (?, ?)";
	private static final String GET_BY_STUDENT_ID_QUERY = "SELECT courses.course_id, courses.course_name, courses.course_description "
			+ "  FROM students_courses " + "       INNER JOIN courses "
			+ "       ON students_courses.course_id = courses.course_id " + " WHERE students_courses.student_id = ?";

	private final DataSource dataSource;

	public CourseDao(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	public void insert(List<Course> courses) throws DAOException {
		if (courses == null)
			throw new IllegalArgumentException("Null is not allowed");
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_COURSE_QUERY)) {
			for (Course course : courses) {
				statement.setString(1, course.getName());
				statement.setString(2, course.getDescription());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			throw new DAOException("Error in insertCourses", e);
		}
	}

	public List<Course> getAll() throws DAOException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_ALL_COURSES_QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			return processResultSet(resultSet);
		} catch (SQLException e) {
			throw new DAOException("Error in getAllCourses", e);
		}
	}

	public List<Course> getByStudentId(int studentId) throws DAOException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_BY_STUDENT_ID_QUERY)) {
			statement.setInt(1, studentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				return processResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException("Error in getByStudentId", e);
		}
	}

	private List<Course> processResultSet(ResultSet resultSet) throws SQLException {
		List<Course> result = new ArrayList<>();
		while (resultSet.next()) {
			Course course = new Course();
			course.setId(resultSet.getInt(1));
			course.setName(resultSet.getString(2));
			course.setDescription(resultSet.getString(3));
			result.add(course);
		}
		return result;
	}
}
