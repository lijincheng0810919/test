package com.jk.model;

import java.io.Serializable;

public class UserBean implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
    private Integer status;
    private String pwd;

    private String sexname;
    private String statusname;

    public String getSexname() {
        return sexname;
    }

    public void setSexname(String sexname) {
        this.sexname = sexname;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
