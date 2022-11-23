package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupDaoTest {

	private DataSource dataSource = new DataSource("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", "sa", "sa");

	@Mock
	private PropertyManager propertyManagerMock = new PropertyManager();

	@InjectMocks
	private GroupDao groupDao = new GroupDao();

	@BeforeEach
	private void setDataSource() {
		PropertyManager propertyManager = new PropertyManager();
		String createTables = "src/test/resources/create_tables.sql";
		try {
			Mockito.lenient().when(propertyManagerMock.getConnectionProperties(anyString())).thenReturn(dataSource);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			propertyManager.createTablesInDatabase(dataSource, createTables);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void insertGroups_ShouldThrowException_WhenGivenNull() {
		assertThrows(IllegalArgumentException.class, () -> groupDao.insert(null));
	}

	@Test
	void insert_ShouldAddNothing_WhenGivenEmptyList() throws DAOException {
		List<Group> expected = new ArrayList<>();
		groupDao.insert(expected);
		List<Group> actual = groupDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void insert_ShouldAddGroupsWithCorrectId_WhenGivenDuplicatedId() throws DAOException {
		List<Group> expected = Arrays.asList(new Group(1, "AC-15", 0), new Group(1, "DC-28", 0));
		groupDao.insert(expected);
		expected.get(1).setId(2);
		List<Group> actual = groupDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void insert_ShouldAddToDatabaseGroups_WhenGivenGroups() throws DAOException {
		List<Group> expected = Arrays.asList(new Group(1, "AC-15", 0), new Group(2, "DC-28", 0));
		groupDao.insert(expected);
		List<Group> actual = groupDao.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void getByStudentsCount_ShouldReturnEmptyList_WhenTableDoNotContainGroups() throws DAOException {
		List<Group> expected = new ArrayList<>();
		List<Group> actual = groupDao.getByStudentsCount(0);
		assertEquals(expected, actual);
	}

}
