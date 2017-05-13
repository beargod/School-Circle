package com.example.administrator.school_circle.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by BigGod on 2017-05-11.
 */
@Entity
public class CourseTable {
    @Id
    private long id;
    /**
     * 第几周
     */
    private int week;
    /**
     * 星期几
     */
    private int weekday;
    /**
     * 第几节课就开始
     */
    private int lesson;
    /**
     * 课程名字
     */
    private String name;
    /**
     * 老师名字
     */
    private String teacher;
    /**
     * 教室地址
     */
    private String classroom;
    /**
     * 课程时长
     */
    private int classDuration;

    @Generated(hash = 1654589823)
    public CourseTable(long id, int week, int weekday, int lesson, String name,
            String teacher, String classroom, int classDuration) {
        this.id = id;
        this.week = week;
        this.weekday = weekday;
        this.lesson = lesson;
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
        this.classDuration = classDuration;
    }

    @Generated(hash = 1681412346)
    public CourseTable() {
    }

    public int getClassDuration() {
        return classDuration;
    }

    public void setClassDuration(int classDuration) {
        this.classDuration = classDuration;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.name + "\n" + this.classroom+"\n"+this.teacher+"\n";
    }
}
