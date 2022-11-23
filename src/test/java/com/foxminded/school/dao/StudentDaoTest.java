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
import com.foxminded.school.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentDaoTest {

	private DataSource dataSource = new DataSource("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", "sa", "sa");

	@Mock
	private PropertyManager propertyManagerMock = new PropertyManager();

	@InjectMocks
	private StudentDao studentDao = new StudentDao();

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
    void getAll_ShouldReturnEmptyListOfStudents_WhenTableIsEmpty() throws DAOException {
        List<Student> expected = new ArrayList<>();
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_ShouldReturnAllStudents_WhenTableIsNotEmpty() throws DAOException {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(2, 2, "Henri", "Koljik"),
                new Student(3, 1, "Bob", "Sudon"),
                new Student(4, 3, "Tim", "Maraket")
        );
        studentDao.insert(expected);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void insert_ShouldThrowException_WhenGivenListOfStudentsNull() {
        Student students = null;
        assertThrows(IllegalArgumentException.class, () -> studentDao.insert(students));
    }

    @Test
    void insert_ShouldAddStudentsWithCorrectId_WhenGivenStudentsWithDuplicatedId() throws DAOException {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(1, 2, "Henri", "Koljik"),
                new Student(1, 1, "Bob", "Sudon"),
                new Student(1, 3, "Tim", "Maraket")
        );
        studentDao.insert(expected);
        expected.get(1).setId(2);
        expected.get(2).setId(3);
        expected.get(3).setId(4);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void insert_ShouldAddStudentsToTable_WhenGivenStudentsList() throws DAOException {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(2, 2, "Henri", "Koljik"),
                new Student(3, 1, "Bob", "Sudon"),
                new Student(4, 3, "Tim", "Maraket")
        );
        studentDao.insert(expected);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void insert_ShouldThrowException_WhenGivenStudentNull() {
        List<Student> students = null;
        assertThrows(IllegalArgumentException.class, () -> studentDao.insert(students));
    }

    @Test
    void insert_ShouldAddStudentWithCorrectId_WhenGivenStudentWithSameId() throws DAOException {
        Student student = new Student(1, 1, "Kolju", "Sudarshan");
        Student duplicatedStudent = new Student(1, 1, "Merlin", "Monto");
        studentDao.insert(student);
        studentDao.insert(duplicatedStudent);
        List<Student> actual = studentDao.getAll();
        List<Student> expected = Arrays.asList(student, duplicatedStudent);
        expected.get(1).setId(2);
        assertEquals(expected, actual);
    }

    @Test
    void insert_ShouldAddStudentToTable_WhenGivenStudent() throws DAOException {
        Student student = new Student(1, 1, "Kolju", "Sudarshan");
        studentDao.insert(student);
        List<Student> actual = studentDao.getAll();
        List<Student> expected = Arrays.asList(student);
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_ShouldNotRemoveAnyStudent_WhenGivenIdDoesNotExist() throws DAOException {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(2, 2, "Henri", "Koljik"),
                new Student(3, 1, "Bob", "Sudon"),
                new Student(4, 3, "Tim", "Maraket")
        );
        studentDao.insert(expected);
        studentDao.deleteById(8);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_ShouldRemoveStudentFromTable_WhenGivenStudentId() throws DAOException {
        List<Student> students = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(2, 2, "Henri", "Koljik"),
                new Student(3, 1, "Bob", "Sudon"),
                new Student(4, 3, "Tim", "Maraket")
        );
        studentDao.insert(students);
        studentDao.deleteById(2);
        List<Student> expected = Arrays.asList(students.get(0), students.get(2), students.get(3));
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void getByCourseName_ShouldThrowException_WhenGivenNull() {
        assertThrows(IllegalArgumentException.class, () -> studentDao.getByCourseName(null));
    }

    @Test
    void assignToCourses_ShouldThrowException_WhenGivenNull() {
        assertThrows(IllegalArgumentException.class, () -> studentDao.assignToCourses(null));
    }

    @Test
    void assignToCourse_ShouldThrowException_WhenNoStudentWithPassedId() {
        assertThrows(DAOException.class, () -> studentDao.assignToCourse(1, 1));
    }

    @Test
    void assignToCourse_ShouldThrowException_WhenNoCourseWithPassedId() throws DAOException {
        Student student = new Student(1, 1, "Kolju", "Sudarshan");
        studentDao.insert(student);
        assertThrows(DAOException.class, () -> studentDao.assignToCourse(1, 1));
    }


    @Test
    void deleteById_ShouldDoNothing_WhenNoStudentByPassedId() throws DAOException {
        List<Student> expected = studentDao.getAll();
        studentDao.deleteById(1);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_ShouldDeleteStudent_WhenGivenStudentId() throws DAOException {
        List<Student> students = Arrays.asList(
                new Student(1, 1, "Somebody", "Sudarshan"),
                new Student(2, 2, "Henri", "Koljik"),
                new Student(3, 1, "Bob", "Sudon"),
                new Student(4, 3, "Tim", "Maraket")
        );
        studentDao.insert(students);
        List<Student> expected = Arrays.asList(students.get(0), students.get(2), students.get(3));
        studentDao.deleteById(2);
        List<Student> actual = studentDao.getAll();
        assertEquals(expected, actual);
    }

}
