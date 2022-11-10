package com.foxminded.school.services;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.models.Group;

public class GroupService {
	 private GroupDao groupDao;

	    public GroupService() {
	    	this.groupDao = new GroupDao();
	    }

	    public List<Group> getByStudentsCount(String count) {
	    int i = Integer.parseInt(count);	
		List<Group> groups = new ArrayList<>();

		try {
		    groups = groupDao.getByStudentsCount(i);
		} catch (DAOException e) {
		    e.printStackTrace();
		}

		return groups;
	    }
}
