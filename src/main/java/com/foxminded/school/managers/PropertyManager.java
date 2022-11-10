package com.foxminded.school.managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.foxminded.school.dao.DataSource;
import com.foxminded.school.exception.DAOException;

public class PropertyManager {

	private PathManager pathManager = new PathManager();

	public DataSource getConnectionProperties(String propertiesFile) throws IOException, ClassNotFoundException {
		DataSource dataSource = parsePropertyFile(propertiesFile);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			throw new ClassNotFoundException("Driver can't be loaded\n" + ex);
		}
		return dataSource;
	}

	public void createTablesInDatabase(DataSource dataSource, String createTablesScript)
			throws IOException, DAOException {
		try {
			Connection connection = dataSource.getConnection();
			System.out.println("Connection established......");
			ScriptRunner scriptRunner = new ScriptRunner(connection);
			try {
				scriptRunner.runScript(new BufferedReader(new FileReader(createTablesScript)));
			} catch (IOException ex) {
				throw new IOException("Cannot read file", ex);
			}
		} catch (SQLException ex) {
			throw new DAOException("Cannot connect to database...", ex);
		}
	}

	private DataSource parsePropertyFile(String propertiesFile) throws IOException {
		try {
			FileInputStream fileStream = new FileInputStream(pathManager.getFilePath(propertiesFile));
			Properties properties = new Properties();
			properties.load(fileStream);
			String url = (String) properties.get("db.url");
			String username = (String) properties.get("db.username");
			String password = (String) properties.get("db.password");
			return new DataSource(url, username, password);
		} catch (IOException ex) {
			throw new IOException("File with properties can't be parsed\n" + ex);
		}
	}
}
