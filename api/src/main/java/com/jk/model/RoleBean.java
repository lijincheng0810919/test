package com.jk.model;

import java.io.Serializable;

public class RoleBean implements Serializable {
    private   Integer   rid;
    private   String   rname;
    private   String   rinfo;
    private   String   value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRinfo() {
        return rinfo;
    }

    public void setRinfo(String rinfo) {
        this.rinfo = rinfo;
    }
}
