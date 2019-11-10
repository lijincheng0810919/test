package com.jk.thread;

import com.jk.model.UserModel;
import com.jk.service.UserService;

import java.util.ArrayList;

public class UserThread implements Runnable {

    private ArrayList<UserModel> userModels;

    private UserService userService;

    @Override
    public void run() {
        userService.saveUser(userModels);
        System.out.println(Thread.currentThread());
    }

    public ArrayList<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(ArrayList<UserModel> userModels) {
        this.userModels = userModels;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
