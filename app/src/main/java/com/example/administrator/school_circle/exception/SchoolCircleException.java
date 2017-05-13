package com.example.administrator.school_circle.exception;

/**
 * Created by BigGod on 2017-05-04.
 */
public class SchoolCircleException extends Exception{


    public SchoolCircleException() {
        super();
    }

    public SchoolCircleException(String msg) {
        super(msg);
    }

    public SchoolCircleException(Throwable e) {
        super(e);
    }

    public SchoolCircleException(String msg, Throwable e) {
        super(msg, e);
    }
}