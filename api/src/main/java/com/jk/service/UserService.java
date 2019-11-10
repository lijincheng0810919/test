package com.jk.service;

import com.jk.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserService {
   /* List<UserBean> findUserList();

    List<DictionaryBean> querySexList();

    List<DictionaryBean> queryStatusList();*/

    /*void addUser2(UserBean userBean);*/

    UserModel queryUserByName(String usname);

    HashMap<String,Object> queryUser(Integer page, Integer rows);

    void saveMenu(MenuBean menu);

    List<MenuBean> queryMenu(Integer powerid);

    List<TreeModel> queryPowerTree(Integer roleid);


    List<RoleBean> queryRole();

    void deletePowerById(Integer powerid);

    void addPower(TreeModel power);

    void updatePowerById(TreeModel power);

    void saveRolePower(Integer roleid, String[] ids);

    UserModel queryUserById(Integer userId);


    void addUser(UserModel user);

    List<String> findPower(Integer usid);

    List<TreeModel> queryTree(Integer usid);

    void saveUser(ArrayList<UserModel> list);

    List<UserModel> findUser();

    Integer queryUserCount();

    List<UserModel> queryUserLimit(int page, int rows);
}
