package models;

public class Score {
    private int id;
    private int studentId;
    private int subjectId;
    private double score;

    public Score() {}

    public Score(int id, int studentId, int subjectId,  double score) {
        this.id = id;
        this.studentId = studentId;
        this.score = score;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }
    public int getStudentId() {
        return studentId;
    }
    public int getSubjectId() {
        return subjectId;
    }
    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "" + score;
    }
}
