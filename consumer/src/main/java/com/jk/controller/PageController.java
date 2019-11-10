package com.jk.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

    @RequestMapping("toList")
    public String tolist(){
        return "list";
    }

    @RequestMapping("toDitu")
    public String toDitu(){
        return "ditu";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("403")
    public String wuquan(){
        return "403";
    }


    @RequestMapping("error")
    public String error(){
        return "error";
    }

    @RequestMapping("toMain")
    public  String  tomain(){
        return "main";
    }

    @RequestMapping("show")
    public  String  toShow(){
        return  "show";
    }

    @RequestMapping("shownav")
    public  String  toShowpower(){
        return  "shownav";
    }


}
