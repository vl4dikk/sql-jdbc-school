package com.foxminded.school.managers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class PathManagerTest {
	
	private PathManager pathManager = new PathManager();

	@Test
	void testGetFilePath() {
		String actual = "";
		try {
			actual = pathManager.getFilePath("db.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String expected = "/C:/workspace/sql-jdbc-school/target/test-classes/db.properties";
		assertEquals(expected, actual);
	}

}
