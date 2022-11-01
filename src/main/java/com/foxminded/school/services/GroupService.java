package com.foxminded.school.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.dao.DataSource;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.exception.DAOException;
import com.foxminded.school.managers.PropertyManager;
import com.foxminded.school.models.Group;

public class GroupService {
	 private static final String DB_PROPERTIES = "db.properties";
	 private GroupDao groupDao;
	 private DataSource dataSource;
	 private PropertyManager propertyManager = new PropertyManager();

	    public GroupService() {
	    	try {
				this.dataSource = propertyManager.getConnectionProperties(DB_PROPERTIES);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
	    	this.groupDao = new GroupDao(dataSource);
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
