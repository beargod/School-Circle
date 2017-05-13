package com.example.administrator.school_circle.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by chentz on 2017/2/8.
 */
@Entity
public class User {
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 年龄
     */
    private int age;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * id
     */
    @Id
    private long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createOn;
    /**
     * 状态
     */
    private String status;
    /**
     * 生日
     */
    private Date birthday;

    private String avatar;

    private String token;

    @Generated(hash = 743271292)
    public User(String telephone, String userName, String email, int age,
            String realName, long id, String account, String password,
            Date createOn, String status, Date birthday, String avatar,
            String token) {
        this.telephone = telephone;
        this.userName = userName;
        this.email = email;
        this.age = age;
        this.realName = realName;
        this.id = id;
        this.account = account;
        this.password = password;
        this.createOn = createOn;
        this.status = status;
        this.birthday = birthday;
        this.avatar = avatar;
        this.token = token;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
