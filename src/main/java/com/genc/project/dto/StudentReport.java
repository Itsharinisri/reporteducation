package com.genc.project.dto;


public class StudentReport {

    private int id; // Student ID
    private String name; // Student Name
    private int progress; // Course progress percentage (e.g., 80)
    private String grade; // Overall letter grade (e.g., "A", "C+")
    private int gradePercent; // Overall numerical grade percentage (e.g., 95)
//    private String lastActive; // Last active timestamp/duration (e.g., "1 day ago")
//    private String lessonsCompleted; // Lessons completed (e.g., "8/10")
//    private String quizzesTaken; // Quizzes taken (e.g., "4/5")
//    private String avgQuizScore; // Average quiz score (e.g., "92%")
//    private String timeSpent; // Total time spent in course (e.g., "20 hours")

    // Constructors
    public StudentReport() {
    }

    public StudentReport(int id, String name, int progress, String grade, int gradePercent) {
//                            String lastActive, String lessonsCompleted, String quizzesTaken,
//                            String avgQuizScore, String timeSpent) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.grade = grade;
        this.gradePercent = gradePercent;
//        this.lastActive = lastActive;
//        this.lessonsCompleted = lessonsCompleted;
//        this.quizzesTaken = quizzesTaken;
//        this.avgQuizScore = avgQuizScore;
//        this.timeSpent = timeSpent;
    }

 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getGradePercent() {
        return gradePercent;
    }

    public void setGradePercent(int gradePercent) {
        this.gradePercent = gradePercent;
    }

//    public String getLastActive() {
//        return lastActive;
//    }
//
//    public void setLastActive(String lastActive) {
//        this.lastActive = lastActive;
//    }
//
//    public String getLessonsCompleted() {
//        return lessonsCompleted;
//    }
//
//    public void setLessonsCompleted(String lessonsCompleted) {
//        this.lessonsCompleted = lessonsCompleted;
//    }
//
//    public String getQuizzesTaken() {
//        return quizzesTaken;
//    }
//
//    public void setQuizzesTaken(String quizzesTaken) {
//        this.quizzesTaken = quizzesTaken;
//    }
//
//    public String getAvgQuizScore() {
//        return avgQuizScore;
//    }
//
//    public void setAvgQuizScore(String avgQuizScore) {
//        this.avgQuizScore = avgQuizScore;
//    }
//
//    public String getTimeSpent() {
//        return timeSpent;
//    }
//
//    public void setTimeSpent(String timeSpent) {
//        this.timeSpent = timeSpent;
//    }
}
