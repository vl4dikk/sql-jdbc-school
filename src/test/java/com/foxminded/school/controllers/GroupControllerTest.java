package com.foxminded.school.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.school.models.Group;
import com.foxminded.school.services.GroupService;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

	@Mock
	GroupService service = new GroupService();

	@InjectMocks
	GroupController controller = new GroupController();

	@Test
	void testFindAllGroups() {
		String count = "11";
		List<Group> groups = new LinkedList<>();
		groups.add(new Group(3, "GF-12", 13));
		groups.add(new Group(5, "AF-18", 10));
		groups.add(new Group(7, "DF-16", 9));
		Mockito.when(service.getByStudentsCount(count)).thenReturn(groups);
		List<String> expected = new LinkedList<>();
		expected.add("<html><body>");
		expected.add("<table border=1 width=50% height=50%>");
		expected.add("<tr><th>Group name</th><th>Students</th><tr>");
		expected.add("<tr><td>GF-12</td><td>13</td></tr>");
		expected.add("<tr><td>AF-18</td><td>10</td></tr>");
		expected.add("<tr><td>DF-16</td><td>9</td></tr>");
		expected.add("</table>");
		expected.add("</html></body>");
		LinkedList<String> actual = controller.findAllGroups(count);
		assertEquals(expected, actual);
	}

}
