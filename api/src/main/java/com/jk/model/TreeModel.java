package com.jk.model;

import java.io.Serializable;
import java.util.List;

public class TreeModel implements Serializable {
    private  Integer  id;
    private Integer  pid;
    private  String  text;
    private  String   url;
    private List<TreeModel>  children;
    private  Boolean  checked;
    private  String  permission;
    private  String   flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }
}
