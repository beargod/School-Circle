package com.example.administrator.school_circle.model;

/**
 * Created by BigGod on 2017-05-12.
 */
public class LeftCell {
    private String course;

    private String time;

    public LeftCell(String course, String time) {
        this.course = course;
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
