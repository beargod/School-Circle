package com.example.administrator.school_circle.model;

/**
 * Created by BigGod on 2017-05-05.
 */
public class Result<T> {
    private int rc;
    private Throwable exception;
    private T ret;

    public Result() {
    }

    public static Result<String> ok() {
        Result result = new Result();
        return result;
    }

    public static Result<String> error(int rc) {
        return error(rc, null, null);
    }

    public static Result<String> error(int rc, String msg) {
        return error(rc, msg, null);
    }

    public static Result<String> error(int rc, Throwable e) {
        return error(rc, null, e);
    }

    public static <T> Result<T> error(int rc, T msg, Throwable e) {
        Result result = new Result();
        result.setRc(rc);
        result.setRet(msg);
        result.setException(e);
        return result;
    }

    public static <T> Result<T> result(T t) {
        Result r = new Result();
        r.setRet(t);
        return r;
    }

    public int getRc() {
        return this.rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public Throwable getException() {
        return this.exception;
    }

    public void setException(Throwable e) {
        this.exception = e;
    }

    public T getRet() {
        return this.ret;
    }

    public void setRet(T ret) {
        this.ret = ret;
    }
}