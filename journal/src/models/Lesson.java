package models;

import data.DataManager;

public class Lesson {
    private int id;
    private int groupId;
    private int teacherId;
    private int subjectId;
    private String dayOfTheWeek;
    private String startTime;
    private String endTime;

    public Lesson(int id, int groupId, int teacherId, int subjectId, String day, String startTime, String endTime) {
        this.groupId = groupId;
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.dayOfTheWeek = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId () { return this.id; }
    public int getGroupId () { return this.groupId; }
    public int getTeacherId () { return this.teacherId; }
    public int getSubjectId () { return this.subjectId; }
    public String getDayOfTheWeek() { return dayOfTheWeek; }
    public String getStartTime () { return startTime; }
    public String getEndTime () { return endTime; }

    @Override
    public String toString() {
        return DataManager.getSubjects().get(subjectId)+ "\t|\tГруппа: " + DataManager.getGroups().get(groupId) + "\t|\tВремя: " + startTime + " - " + endTime;
    }

}
