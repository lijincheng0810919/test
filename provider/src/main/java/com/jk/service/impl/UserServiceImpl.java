package com.jk.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.jk.common.CommonConf;
import com.jk.mapper.UserMapper;
import com.jk.model.*;
import com.jk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public List<TreeModel> queryTree(Integer usid) {
        String TREE_CACHE = "TREE_CACHE";

        if (redisTemplate.hasKey(TREE_CACHE)){
            Object range = redisTemplate.opsForList().range(TREE_CACHE, 0, -1);
            List<TreeModel> list = (List<TreeModel>) range;
            return list;
        }else{
            int  pid=0;
            List<TreeModel> list = findNode(pid,usid);
            for (TreeModel treeModel : list) {
                redisTemplate.opsForList().leftPush(TREE_CACHE,treeModel);
            }
            return findNode(pid,usid);
        }
    }

    @Override
    public void saveUser(ArrayList<UserModel> list) {
        userMapper.saveUser(list);
    }

    @Override
    public List<UserModel> findUser() {
        return userMapper.findUser();
    }

    @Override
    public Integer queryUserCount() {
        return userMapper.queryUserCount();
    }

    @Override
    public List<UserModel> queryUserLimit(int page, int rows) {
        Integer start = (page-1)*rows;
        Integer end = rows;
        return userMapper.queryUser(start,end);
    }

    private List<TreeModel> findNode(int pid,Integer usid) {
        List<TreeModel> list = userMapper.queryTreeByUsid(pid,usid);
        for (TreeModel treeBean : list) {
            Integer id = treeBean.getId();
            List<TreeModel> nodes = findNode(id,usid);
            treeBean.setChildren(nodes);
        }
        return list;
    }

/*

    @Override
    public List<UserBean> findUserList() {
        HashMap<String, String> map = new HashMap<>();

        List<UserBean> userBeanList = userMapper.findUserList();

        List<DictionaryBean> dictionaryBeans = userMapper.querySexList();
        for (DictionaryBean dictionaryBean : dictionaryBeans) {
            map.put(dictionaryBean.getNo().toString(),dictionaryBean.getName());
        }

        List<DictionaryBean> dictionaryBeans1 = userMapper.queryStatusList();
        for (DictionaryBean dictionaryBean : dictionaryBeans1) {
            map.put(dictionaryBean.getNo().toString(),dictionaryBean.getName());
        }

        for (UserBean userBean : userBeanList) {
            Integer sex = userBean.getSex();
            String s = map.get(sex.toString());
            userBean.setSexname(s);

            Integer status = userBean.getStatus();
            String s1 = map.get(status.toString());
            userBean.setStatusname(s1);
        }
        return userBeanList;
    }
*/

  /*  @Override
    public List<DictionaryBean> querySexList() {
        String SEXCACHE = "SEX_CACHE";
        if (redisTemplate.hasKey(SEXCACHE)) {
            String string = redisTemplate.opsForValue().get(SEXCACHE).toString();
            List<DictionaryBean> parseArray = JSON.parseArray(string, DictionaryBean.class);
            return parseArray;
        }else {
            List<DictionaryBean> dictionaryBeans = userMapper.querySexList();
            String jsonString = JSON.toJSONString(dictionaryBeans);
            for (DictionaryBean dictionaryBean : dictionaryBeans) {
                redisTemplate.opsForValue().set(SEXCACHE, jsonString, 30, TimeUnit.MINUTES);
            }
            return dictionaryBeans;
        }
    }*/
//
//    @Override
//    public List<DictionaryBean> queryStatusList() {
//        return userMapper.queryStatusList();
//    }

  /*  @Override
    public void addUser2(UserBean userBean) {
        userMapper.addUser2(userBean);
    }*/

    @Override
    public UserModel queryUserByName(String usname) {
        return userMapper.queryUserByName(usname);
    }


    @Override
    public HashMap<String,Object> queryUser(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer count = userMapper.queryUserCount();

        Integer start = (page-1)*rows;
        Integer end = rows;
        List<UserModel> list = userMapper.queryUser(start,end);
        map.put("total",count);
        map.put("rows",list);
        return map;
    }


    @Override
    public void saveMenu(MenuBean menu) {
        userMapper.saveMenu(menu);
    }

    @Override
    public List<MenuBean> queryMenu(Integer powerid) {
        return userMapper.queryMenu(powerid);
    }

    @Override
    public List<TreeModel> queryPowerTree(Integer roleid) {
        List<TreeModel> rolePower = userMapper.queryPowerTreeByRoleid(roleid);

        int pid = 0;
        //查询一级节点
        //提取公共方法的快捷键：alt+shift+m
        List<TreeModel> list = queryPowerNodes(pid,rolePower);

        //添加虚拟的根节点
        TreeModel tree = new TreeModel();
        tree.setId(0);
        tree.setPid(-1);
        tree.setText("根节点");
        tree.setChildren(list);

        ArrayList<TreeModel> list2 = new ArrayList<TreeModel>();
        list2.add(tree);
        return list2;
    }

    private List<TreeModel> queryPowerNodes(int pid, List<TreeModel> rolePower) {
        List<TreeModel> list = userMapper.queryTree2(pid);

        for (TreeModel treeBean : list) {
            Integer id = treeBean.getId();
            //查询对应的子节点
            List<TreeModel> nodes = queryPowerNodes(id,rolePower);//递归：自己调自己
            treeBean.setChildren(nodes);

            //把所有的权限，跟当前角色拥有的权限比较：checked属性：true
            for (TreeModel treeBean2 : rolePower) {
                Integer id3 = treeBean2.getId();
                if(nodes.size()<=0 && id==id3){//是子节点 并且 有权限
                    treeBean.setChecked(true);
                }
            }
        }
        return list;
    }

    @Override
    public List<RoleBean> queryRole() {
        String ROLECACHE = CommonConf.ROLECACHE;
        if (redisTemplate.hasKey(ROLECACHE)) {
            String s = redisTemplate.opsForValue().get(ROLECACHE).toString();
            List<RoleBean> roleBeanList = JSON.parseArray(s, RoleBean.class);
            return roleBeanList;
        }else {
            List<RoleBean> roleBeanList = userMapper.queryRole();
            String s = JSON.toJSONString(roleBeanList);
            for (RoleBean roleBean : roleBeanList) {
                redisTemplate.opsForValue().set(ROLECACHE,roleBean,3, TimeUnit.MINUTES);
            }
            return roleBeanList;
        }



    }

    @Override
    public void deletePowerById(Integer powerid) {
        userMapper.deletePowerById(powerid);
    }

    @Override
    public void addPower(TreeModel power) {
        userMapper.addPower(power);
    }

    @Override
    public void updatePowerById(TreeModel power) {
        userMapper.updatePowerById(power);
    }

    @Override
    public void saveRolePower(Integer roleid, String[] ids) {
        //删除
        userMapper.deleteByRoleid(roleid);
        //新增
        if(ids.length>0){
            userMapper.addRolePower(roleid,ids);
        }
    }

    @Override
    public UserModel queryUserById(Integer userId) {
        return userMapper.queryUserById(userId);
    }

    @Override
    public void addUser(UserModel user) {
        Integer usid = user.getUsid();
        if(usid!=null){//修改
            //修改用户
            userMapper.updateUser(user);
            //修改用户角色中间表 1、删除 2、新增
            //删除：根据用户id，用户角色中间删除
            userMapper.deleteUserRole(usid);
        }else{//新增
            //新增用户
            userMapper.addUser(user);
        }
        //新增用户角色中间表：批量新增
        String roleids = user.getRid();
        String[] arrId = roleids.split(",");
        userMapper.addUserRole(arrId,user.getUsid());
    }




    @Override
    public List<String> findPower(Integer usid) {
        return userMapper.findPower(usid);
    }


}
