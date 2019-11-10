package com.jk.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private  Integer  usid;
    private  String  usname;
    private  String  uspass;
    private String  code;
    private  String  rname;
    private  String  rid;
private  Integer  id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUsid() {
        return usid;
    }

    public void setUsid(Integer usid) {
        this.usid = usid;
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getUspass() {
        return uspass;
    }

    public void setUspass(String uspass) {
        this.uspass = uspass;
    }
}
