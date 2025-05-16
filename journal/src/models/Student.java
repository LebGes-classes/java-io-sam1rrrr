package models;

import java.util.ArrayList;
import java.util.HashMap;

import data.DataManager;

public class Student implements Comparable<Student> {
    private int id;
    private String fullName;
    private int groupId;
    String groupNumber;
    private HashMap<Subject, ArrayList<Score>> scores;

    public Student(int id, String fullName, int groupId) {
        this.id = id;
        this.fullName = fullName;
        this.groupId = groupId;
        scores = new HashMap<Subject, ArrayList<Score>>();
        groupNumber = DataManager.getGroups().get(groupId).getNumber();
    }

    public HashMap<Subject, ArrayList<Score>> getScores() {
        return scores;
    }
    public String getFullName() { return fullName; }
    public int getId() {
        return id;
    }
    public String getGroupNumber() {
        return groupNumber;
    }
    public int getGroupId() { return groupId; }

    public void addScore(Score score, int subjectId){
        Subject subject = DataManager.getSubjects().get(subjectId);
        if (scores.containsKey(subject)){
            scores.get(subject).add(score);
        }
        else{
            scores.put(subject, new ArrayList<>());
            scores.get(subject).add(score);
        }
    }

    @Override
    public int compareTo(Student o) {
        int cmp = fullName.compareTo(o.fullName);
        if (cmp == 0){
            cmp = Integer.compare(id, o.getId());
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "Студент: " + fullName + "\t|\tГруппа: " + DataManager.getGroups().get(groupId).toString();
    }
}
