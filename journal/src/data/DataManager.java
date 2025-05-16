package data;

import models.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static Map<Integer, Teacher> teachers = new HashMap<>();
    private static Map<Integer, Group> groups = new HashMap<>();
    private static Map<Integer, Subject> subjects = new HashMap<>();
    private static Map<Integer, Student> students = new HashMap<>();
    private static Map<Integer, Score> scores = new HashMap<>();

    public static Map<Integer, Subject> getSubjects() { return subjects; }
    public static Map<Integer, Teacher> getTeachers() { return teachers; }
    public static Map<Integer, Group> getGroups() { return groups; }
    public static Map<Integer,Student> getStudents() { return students; }
    public static Map<Integer, Score> getScores() { return scores; }

    public static void saveSerializedForm(Student student) {
        String folderPath = "journal/resources/students/";
        String fileName = student.toString() + ".json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            File file = new File(folderPath + fileName);
            mapper.writeValue(file, student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Информация о студенте была сохранена в папке загрузки в формате json");
    }

    public static void saveAllStudents() {
        String PATH = "journal/resources/students/students.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            File file = new File(PATH);
            mapper.writeValue(file, students.values());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTeacher(Teacher teacher, int id) {
        teachers.put(id, teacher);
    }
    public static void addStudent(Student student, int id) {
        students.put(id, student);
    }
    public static void addStudentToGroup(Student student, int groupId) {
        groups.get(groupId).addStudent(student);
    }
    public static void addGroup(Group group, int id) {
        groups.put(id, group);
    }
    public static void addSubject(Subject subject, int id) {
        subjects.put(id, subject);
    }
    public static void addScore(Score score, int id) {
        scores.put(id, score);
    }

    public static int getLastScoreId(){
        int lastScore = 0;
        for (int i : scores.keySet()) {
            lastScore = Math.max(lastScore, i);
        }
        return lastScore;
    }

    public static void sort(){
        for (Group group : groups.values()){
            Collections.sort(group.getStudents());
        }
    }
    public static void load(){
        ExcelReader.readTeachersFromExcel();
        ExcelReader.readGroupsFromExcel();
        ExcelReader.readStudentsFromExcel();
        ExcelReader.readSubjectsFromExcel();
        ExcelReader.readScoresFromExcel();
        ExcelReader.readScheduleFromExcel();
        DataManager.sort();
    }
}
