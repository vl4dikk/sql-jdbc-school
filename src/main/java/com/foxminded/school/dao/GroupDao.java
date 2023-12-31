package com.foxminded.school.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Group;

public class GroupDao {
	private static final String GET_ALL_GROUPS_QUERY = "SELECT * FROM groups";
	private static final String INSERT_GROUP_QUERY = "INSERT INTO groups (group_name) VALUES (?)";
	private static final String GET_BY_STUDENTS_COUNT_QUERY = "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) "
			+ "  FROM groups " + "       LEFT JOIN students " + "       ON students.group_id = groups.group_id "
			+ " GROUP BY groups.group_id " + "HAVING COUNT(*) <= ?";

	private DataSource dataSource;
	private static final String DB_PROPERTIES = "db.properties";
	private PropertyManager propertyManager = new PropertyManager();

	public GroupDao() {
		try {
			this.dataSource = propertyManager.getConnectionProperties(DB_PROPERTIES);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void insert(List<Group> groups) throws DAOException {
		if (groups == null)
			throw new IllegalArgumentException("Null is not allowed");
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_GROUP_QUERY)) {
			for (Group group : groups) {
				statement.setString(1, group.getName());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			throw new DAOException("Error in insertGroups", e);
		}
	}

	public List<Group> getAll() throws DAOException {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_GROUPS_QUERY);
			ResultSet resultSet = statement.executeQuery();
			return processResultSet(resultSet);
		} catch (SQLException e) {
			throw new DAOException("Error in getGroups", e);
		}
	}

	public List<Group> getByStudentsCount(int count) throws DAOException {
		List<Group> result = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_BY_STUDENTS_COUNT_QUERY)) {
			statement.setInt(1, count);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Group group = new Group();
					group.setId(resultSet.getInt(1));
					group.setName(resultSet.getString(2));
					group.setStudentsCount(resultSet.getInt(3));
					result.add(group);
				}
				return result;
			}
		} catch (SQLException e) {
			throw new DAOException("Error in getGroupsByStudentsCount", e);
		}
	}

	private List<Group> processResultSet(ResultSet resultSet) throws SQLException {
		List<Group> result = new ArrayList<>();
		while (resultSet.next()) {
			Group group = new Group();
			group.setId(resultSet.getInt(1));
			group.setName(resultSet.getString(2));
			result.add(group);
		}
		return result;
	}

}
