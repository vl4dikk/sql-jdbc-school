package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Course;
import com.foxminded.school.models.Student;

public class StudentDao {
	    private static final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM students";
	    private static final String DELETE_BY_ID_QUERY = "DELETE FROM students WHERE student_id = ?";
	    private static final String ASSIGN_TO_COURSE_QUERY =
	            "INSERT INTO students_courses (student_id, course_id) " +
	            "     VALUES (?, ?)";
	    private static final String INSERT_STUDENTS_QUERY =
	            "INSERT INTO students (group_id, first_name, last_name) " +
	            "     VALUES (?, ?, ?)";
	    private static final String GET_BY_COURSE_NAME =
	            "SELECT students.student_id, students.group_id, students.first_name, students.last_name " +
	            "  FROM students_courses " +
	            "       INNER JOIN students " +
	            "       ON students_courses.student_id = students.student_id " +

	            "       INNER  JOIN courses " +
	            "       ON students_courses.course_id = courses.course_id " +
	            " WHERE courses.course_name = ?";
	    private static final String DELETE_FROM_COURSE =
	            "DELETE " +
	            "  FROM students_courses " +
	            " WHERE student_id = ? " +
	            "   AND course_id = ?";

	    private final DataSource dataSource;

	    public StudentDao(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    public List<Student> getAll() throws DAOException {
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(GET_ALL_STUDENTS_QUERY);
	             ResultSet resultSet = statement.executeQuery()) {
	            return processResultSet(resultSet);
	        } catch (SQLException e) {
	            throw new DAOException("Cannot run getAllStudents method", e);
	        }
	    }

	    public void insert(List<Student> students) throws DAOException {
	        if (students == null)
	            throw new IllegalArgumentException("Null in not allowed");
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENTS_QUERY)) {
	            for (Student student : students) {
	                statement.setInt(1, student.getGroupId());
	                statement.setString(2, student.getFirstName());
	                statement.setString(3, student.getLastName());
	                statement.addBatch();
	            }
	            statement.executeBatch();
	        } catch (SQLException e) {
	            throw new DAOException("Error in insertStudents", e);

	        }
	    }

	    public void insert(Student student) throws DAOException {
	        if (student == null)
	            throw new IllegalArgumentException("Null is not allowed");
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENTS_QUERY)) {
	            statement.setInt(1, student.getGroupId());
	            statement.setString(2, student.getFirstName());
	            statement.setString(3, student.getLastName());
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new DAOException("Error in insertStudent", e);
	        }
	    }

	    public void deleteById(int studentId) throws DAOException {
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
	            statement.setInt(1, studentId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new DAOException("Error in deleteStudentById", e);
	        }
	    }

	    public List<Student> getByCourseName(String courseName) throws DAOException {
	        if (courseName == null)
	            throw new IllegalArgumentException("Null is now allowed");
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(GET_BY_COURSE_NAME)) {
	            statement.setString(1, courseName);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                return processResultSet(resultSet);
	            }
	        } catch (SQLException e) {
	            throw new DAOException("Error in getStudentsByCourseName", e);
	        }
	    }

	    public void assignToCourses(Map<Student, List<Course>> map) throws DAOException {
	        if (map == null)
	            throw new IllegalArgumentException("Null is not allowed");
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_COURSE_QUERY)) {
	            for (Map.Entry<Student, List<Course>> entry : map.entrySet()) {
	                Student student = entry.getKey();
	                for (Course course : entry.getValue()) {
	                    statement.setInt(1, student.getId());
	                    statement.setInt(2, course.getId());
	                    statement.addBatch();
	                }
	            }
	            statement.executeBatch();
	        } catch (SQLException e) {
	            throw new DAOException("Error in assignToCourses", e);
	        }
	    }

	    public void assignToCourse(int studentId, int courseId) throws DAOException {
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_COURSE_QUERY)) {
	            statement.setInt(1, studentId);
	            statement.setInt(2, courseId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new DAOException("Error in assignToCourse", e);
	        }
	    }

	    public void deleteFromCourse(int studentId, int courseId) throws DAOException {
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(DELETE_FROM_COURSE)) {
	            statement.setInt(1, studentId);
	            statement.setInt(2, courseId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new DAOException("Error in deleteFromCourse", e);
	        }
	    }

	    private List<Student> processResultSet(ResultSet resultSet) throws SQLException {
	        List<Student> result = new ArrayList<>();
	        while (resultSet.next()) {
	            Student student = new Student();
	            student.setId(resultSet.getInt(1));
	            student.setGroupId(resultSet.getInt(2));
	            student.setFirstName(resultSet.getString(3));
	            student.setLastName(resultSet.getString(4));
	            result.add(student);
	        }
	        return result;
	    }
}
