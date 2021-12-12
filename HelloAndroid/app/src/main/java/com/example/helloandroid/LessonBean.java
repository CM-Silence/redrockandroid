package com.example.helloandroid;

public class LessonBean {
    private String fullName;
    private String name;
    private String classroom;
    private String time;

    public LessonBean(String fullName, String name, String classroom, String time) {
        this.fullName = fullName;
        this.name = name;
        this.classroom = classroom;
        this.time = time;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
