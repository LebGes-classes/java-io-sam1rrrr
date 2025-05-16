package ui;

import models.*;
import data.DataManager;

import java.util.*;

import static data.DataManager.saveAllStudents;
import static data.ExcelWriter.saveAllDataToExcel;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Teacher currentTeacher;
    private static String[] daysOfTheWeek = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

    public static void mainMenu(){
        clearConsole();
        System.out.println("Выберите себя из следующего списка:");
        for(Teacher teacher: DataManager.getTeachers().values()){
            System.out.println(teacher.getId() +") " + teacher);
        }
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        if (choice == 0) {
            saveAllStudents();
            saveAllDataToExcel();
            System.exit(0);
        }
        currentTeacher = DataManager.getTeachers().get(choice);
        if (currentTeacher == null){
            mainMenu();
        }
        menuForTeacher();
    }

    public static void menuForTeacher(){
        clearConsole();
        System.out.println("Здравствуйте, " + currentTeacher.getFirstName() + " " + currentTeacher.getPatronymic());
        System.out.println("Выберите действие: ");
        System.out.println("1) Мое расписание");
        System.out.println("2) Список групп");
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                scheduleForTeacher();
                break;
            case 2:
                groupsForTeacher();
                break;
            case 0:
                mainMenu();
            default:
                menuForTeacher();
                break;
        }
    }

    public static void scheduleForTeacher(){
        clearConsole();
        System.out.println("1) Понедельник");
        System.out.println("2) Вторник");
        System.out.println("3) Среда");
        System.out.println("4) Четверг");
        System.out.println("5) Пятница");
        System.out.println("6) Суббота");
        System.out.println("7) Вся неделя");
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        switch (choice){
            case 7:
                printScheduleForWeek();
                break;
            case 1:
                printScheduleForDay("Понедельник");
                break;
            case 2:
                printScheduleForDay("Вторник");
                break;
            case 3:
                printScheduleForDay("Среда");
                break;
            case 4:
                printScheduleForDay("Четверг");
                break;
            case 5:
                printScheduleForDay("Пятница");
                break;
            case 6:
                printScheduleForDay("Суббота");
                break;
            case 0:
                menuForTeacher();
            default:
                scheduleForTeacher();
        }
    }

    public static void printScheduleForDay(String day){
        clearConsole();
        Map<String, ArrayList<Lesson>> schedule = currentTeacher.getSchedule();
        List<Lesson> lessons = schedule.get(day);
        System.out.println(day + ":");
        if (lessons.isEmpty()) {
            System.out.println("В этот день пар нет");
        } else {
            for(Lesson lesson: lessons){
                System.out.println(lesson);
            }
        }

        System.out.println("Нажмите Enter для выхода");
        scanner.nextLine();
        scanner.nextLine();
        scheduleForTeacher();
    }

    public static void printScheduleForWeek(){
        clearConsole();
        Map<String, ArrayList<Lesson>> schedule = currentTeacher.getSchedule();
        for(String day: daysOfTheWeek){
            List<Lesson> lessons = schedule.get(day);
            System.out.println(day + ":");
            if (lessons.isEmpty()) {
                System.out.println("В этот день пар нет");
            } else {
                for(Lesson lesson: lessons){
                    System.out.println(lesson);
                }
            }
        }
        System.out.println("Нажмите Enter для выхода");
        scanner.nextLine();
        scanner.nextLine();
        scheduleForTeacher();
    }

    public static void groupsForTeacher() {
        clearConsole();
        System.out.println("Введите номер группы: ");
        List<Group> teacherGroups = new ArrayList<>(currentTeacher.getGroups().values());
        Collections.sort(teacherGroups);
        int i = 1;
        for (Group group : teacherGroups) {
            System.out.println(i + ") " + group);
            i++;
        }
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        if (choice == 0) {
            menuForTeacher();
        } else {
            studentsInGroup(String.valueOf(teacherGroups.get(choice - 1)));
        }
    }

    public static void studentsInGroup(String groupNumber){
        clearConsole();
        System.out.println("Группа " + groupNumber + ":");
        if (currentTeacher.getGroups().get(groupNumber) == null) {
            groupsForTeacher();
        }
        List<Student> students = currentTeacher.getGroups().get(groupNumber).getStudents();
        int i = 1;
        for(Student student: students){
            System.out.println(i + ") " + student);
            i++;
        }
        System.out.println("0) Выход");
        System.out.println();
        System.out.println("Выберите студента: ");
        int choice = scanner.nextInt();
        if (choice == 0) {
            groupsForTeacher();
        } else {
            try {
                Student currentStudent = students.get(choice - 1);
                printStudent(currentStudent);
            }
            catch (IndexOutOfBoundsException e) {
                studentsInGroup(groupNumber);
            }
        }
    }

    public static void printStudent(Student student){
        clearConsole();
        System.out.println(student);
        System.out.println("Выберите действие: ");
        System.out.println("1) Управлять оценками студента");
        System.out.println("2) Сохранить информацию о студенте");
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        switch (choice){
            case 0:
                studentsInGroup(student.getGroupNumber());
                break;
            case 1:
                printStudentsScores(student);
                break;
            case 2:
                DataManager.saveSerializedForm(student);
                System.out.println("Нажмите Enter для выхода");
                scanner.nextLine();
                scanner.nextLine();
                printStudent(student);
                break;
            default:
                printStudent(student);
        }
    }

    public static void printStudentsScores(Student student){
        clearConsole();
        System.out.println(student);
        Subject subject = currentTeacher.getMainSubject();
        System.out.println(subject + ":");
        ArrayList<Score> scores = student.getScores().get(subject);
        if(scores == null) {
            System.out.println("По этому предмету у студента нет оценок");
        }
        else {
            for (Score score : scores) {
                System.out.print(score + "\t|\t");
            }
            System.out.println();
        }

        System.out.println("1) Поставить оценку");
        System.out.println("0) Выход");
        int choice = scanner.nextInt();
        if (choice == 1) {
            setScore(student, subject);
        }
        printStudent(student);
    }

    public static void setScore(Student student, Subject subject){
        clearConsole();
        System.out.println("Введите оценку:");
        double scoreValue = scanner.nextDouble();
        if(scoreValue < 2 || scoreValue > 5){
            setScore(student, subject);
        }
        int newScoreId = DataManager.getLastScoreId()+1;
        Score score = new Score(newScoreId, student.getId(), subject.getId(), scoreValue);
        student.addScore(score, subject.getId());
        DataManager.addScore(score, newScoreId);
        printStudentsScores(student);
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
