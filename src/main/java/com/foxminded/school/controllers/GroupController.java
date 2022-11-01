package com.foxminded.school.controllers;

import java.util.LinkedList;
import java.util.List;

import com.foxminded.school.models.Group;
import com.foxminded.school.services.GroupService;

public class GroupController {

	private GroupService service = new GroupService();
	
	public LinkedList<String> findAllGroups (String string) {
		LinkedList<String> answer = new LinkedList<>();
		List<Group> groups = new LinkedList<Group>();
			groups = service.getByStudentsCount(string);
			
			answer.add("<html><body>");
			answer.add("<table border=1 width=50% height=50%>");
			answer.add("<tr><th>Group name</th><th>Students</th><tr>");
		for (Group group : groups) {
			String name = group.getName();
			String count = String.valueOf(group.getStudentsCount());
			answer.add("<tr><td>" + name + "</td><td>" + count + "</td></tr>");
		}
		answer.add("</table>");
		answer.add("</html></body>");
		return answer;
	}

}
