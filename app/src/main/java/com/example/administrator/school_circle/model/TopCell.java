package com.example.administrator.school_circle.model;

/**
 * Created by BigGod on 2017-05-12.
 */
public class TopCell {
    private String date;

    private String weekday;

    public TopCell(String date, String weekday) {
        this.date = date;
        this.weekday = weekday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

}
