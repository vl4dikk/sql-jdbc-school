package com.foxminded.school.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	private GroupDao groupDao;

	@InjectMocks
	private GroupService service = new GroupService();

	@Test
	void testGetByStudentsCount_ShouldReturnListOfGroupsWithExactCount_WhenGivenCount() {
		List<Group> groups = new LinkedList<>();
		int i = 15;
		String count = String.valueOf(i);
		groups.add(new Group(3, "GF-12", 13));
		groups.add(new Group(5, "AF-18", 10));
		groups.add(new Group(7, "DF-16", 9));
		
		try {
			Mockito.when(groupDao.getByStudentsCount(i)).thenReturn(groups);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		List<Group> actual = service.getByStudentsCount(count);
		assertEquals(groups, actual);
	}

}
