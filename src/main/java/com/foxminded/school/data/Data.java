package com.foxminded.school.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.foxminded.school.models.Course;
import com.foxminded.school.models.Group;
import com.foxminded.school.models.Student;

public class Data {
    private static final int UPPER_LETTER_A_CHARCODE = 65;
    private static final int UPPER_LETTER_Z_CHARCODE = 90;
    private static final int STREAM_SIZE = 2;
    private static final int STUDENTS_AMOUNT = 200;
    private final Random random = new Random();
    private final List<String> firstNames;
    private final List<String> lastNames;
    private final int[] studentsInGroup;
    private final List<Course> courses;
    private ClassLoader classLoader = getClass().getClassLoader();

    public List<Group> getGroups() {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Group group = new Group();
            group.setId(i + 1);
            StringBuilder course = new StringBuilder();
            String letters = random.ints(STREAM_SIZE, UPPER_LETTER_A_CHARCODE, UPPER_LETTER_Z_CHARCODE)
                    .mapToObj(upperLetter -> String.valueOf((char) upperLetter))
                    .collect(Collectors.joining());
            String numbers = random.ints(2, 0, 9)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());
            group.setName(course.append(letters + "-" + numbers).toString());
            list.add(group);
        }
        return list;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Student> getStudents(List<Group> groups) {
        List<Student> students = getStudentsWithNames();
        return assignStudentsToGroups(students, groups);
    }

    private List<Student> getStudentsWithNames() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < STUDENTS_AMOUNT; i++) {
            Student student = new Student();
            student.setId(i + 1);
            student.setFirstName(getRandomFirstName());
            student.setLastName(getRandomLastName());
            list.add(student);
        }
        return list;
    }

    public Map<Student, List<Course>> getStudentsCourses(List<Student> students, List<Course> courses) {
        Map<Student, List<Course>> result = new HashMap<>();
        for (Student student : students) {
            int coursesCount = getRandomCoursesCount();
            List<Course> coursesTmp = new ArrayList<>(courses);
            List<Course> studentCourses = new ArrayList<>();
            for (int i = 0; i < coursesCount; i++) {
                int randomCourseIndex = getRandomCourseIndex(coursesTmp);
                Course studentCourse = coursesTmp.get(randomCourseIndex);
                studentCourses.add(studentCourse);
                coursesTmp.remove(randomCourseIndex);
            }
            result.put(student, studentCourses);
        }
        return result;
    }

    private List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        List<Student> studentsTempList = new ArrayList<>(students);
        for (Group group : groups) {
            int studentsCount = getRandomStudentsCount();
            for (int i = 0; i < studentsCount; i++) {
                if (studentsCount > students.size() || !studentsTempList.isEmpty()) {
                    int randomStudentIndex = getRandomStudentIndex(studentsTempList);
                    int studentId = studentsTempList.get(randomStudentIndex).getId();
                    studentsTempList.remove(randomStudentIndex);
                    assignGroupIdToStudent(students, studentId, group.getId());
                }
            }
        }
        return students;
    }

    private void assignGroupIdToStudent(List<Student> students, int studentId, int groupId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.setGroupId(groupId);
            }
        }
    }

    private String getRandomFirstName() {
        return firstNames.get(random.nextInt(firstNames.size() - 1));
    }

    private String getRandomLastName() {
        return lastNames.get(random.nextInt(lastNames.size() - 1));
    }

    private int getRandomStudentsCount() {
        return studentsInGroup[random.nextInt(studentsInGroup.length - 1)];
    }

    private int getRandomStudentIndex(List<Student> students) {
        if (students.size() > 0) {
            return random.nextInt(students.size());
        } else return 0;
    }

    private int getRandomCoursesCount() {
        return random.nextInt(3) + 1;
    }

    private int getRandomCourseIndex(List<Course> courses) {
        return random.nextInt(courses.size());
    }
    
    private List <String> getDataFromFile (String fileName) {
	return new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(fileName))).lines().collect(Collectors.toList());
    }
    
    private List<Course> createCourses(String fileName) {
    	String spliterator = "_";
    	List <String> strings = getDataFromFile(fileName);

    	List <Course> courses= strings.stream().map(line -> line.split(spliterator))
    	                .map(course -> new Course(Integer.parseInt(course[0]), course[1], course[2]))
    	                .collect(Collectors.toList());

    	return courses;
        }

    public Data() {
        this.firstNames = getDataFromFile("FirstNames.txt");

        this.lastNames = getDataFromFile("LastNames.txt");
        
        this.courses = createCourses("courses.txt");

        this.studentsInGroup = new int[]{
                0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    }

}
