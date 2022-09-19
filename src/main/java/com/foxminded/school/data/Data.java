package com.foxminded.school.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.foxminded.school.models.Course;
import com.foxminded.school.models.Group;
import com.foxminded.school.models.Student;

public class Data {
    private static final int UPPER_LETTER_A_CHARCODE = 65;
    private static final int UPPER_LETTER_Z_CHARCODE = 90;
    private static final int STREAM_SIZE = 2;
    private static final int STUDENTS_AMOUNT = 200;
    private final Random random = new Random();
    private final String[] firstNames;
    private final String[] lastNames;
    private final int[] studentsInGroup;
    private final List<Course> courses;

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
        return firstNames[random.nextInt(firstNames.length - 1)];
    }

    private String getRandomLastName() {
        return lastNames[random.nextInt(lastNames.length - 1)];
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

    public Data() {
        this.firstNames = new String[]{
                "Vladyslav",
                "Orest",
                "Vitaliy",
                "Oleksandr",
                "Ivan",
                "Yaroslav",
                "Anton",
                "Victor",
                "Serhiy",
                "Andrii",
                "Svyatoslav",
                "Grigoriy",
                "David",
                "Mykola",
                "Bogdan",
                "Dmytro",
                "Mykhailo",
                "Oleksii",
                "Leonid",
                "Petro"};

        this.lastNames = new String[]{
                "Valchuk",
                "Pavlychko",
                "Kukuruza",
                "Ravchuk",
                "Bunyo",
                "Shukhevych",
                "Bandera",
                "Kuk",
                "Stetsko",
                "Oliinyk",
                "Kotyk",
                "Svystun",
                "Basiuk",
                "Kachan",
                "Lutskyi",
                "Sydor",
                "Hasyn",
                "Oberyshyn",
                "Kliachkivskiy",
                "Dipsize"};

        this.courses = Arrays.asList(
                new Course(1,
                        "Math",
                        "Mathematics is the science and study of quality, structure, space, and change."),
                new Course(2,
                        "Biology",
                        "Study of living things and their vital processes."),
                new Course(3,
                        "Chemistry",
                        "Chemistry is one of three central branches educational science."),
                new Course(4,
                        "Computer Science",
                        "How we use computers and computer programmhas utterly " +
                                "defined the world we live in today and its computcientists whom " +
                                "connect the abstract with concrete creating troducts we use every day."),
                new Course(5,
                        "Physics",
                        "Survey of major concepts, methods, and applications of physics. "),
                new Course(6,
                        "Economics",
                        "Social science of which factors determine the production " +
                                "adistribution goods and services in a consumer, capitalist society."),
                new Course(7,
                        "History",
                        "Historians use evidence to try to understand why people " +
                                "believwhat they believed and why they did what they did."),
                new Course(8,
                        "Art",
                        "Students will explore basic art media and techniques, such as drawing, painting, " +
                                "graphic design, photography, collage, ceramics, printmaking, and sculpture and more!"),
                new Course(9,
                        "Robotics",
                        "Robotics is a branch of mechanical engineering, " +
                                "electricengineering, electronic engineering and computer science."),
                new Course(10,
                        "Sociology",
                        "Sociology is the scientific study of behaviour by people in" +
                                " the society in which they live, how it came about, is organised and " +
                                "developed, and what it may become in the future.")
        );

        this.studentsInGroup = new int[]{
                0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    }

}
