package com.jk.mapper;

import com.jk.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@Mapper
public interface UserMapper {

    List<TreeModel> queryTreeByUsid(@Param("pid") int pid, @Param("usid") Integer usid);

    /*@Select("select * from t_student")
    List<UserBean> findUserList();

    @Select("select no,name from t_dic where pid=100")
    List<DictionaryBean> querySexList();

    @Select("select no,name from t_dic where pid=200")
    List<DictionaryBean> queryStatusList();*/

    /*void addUser2(UserBean userBean);*/

    @Select("select * from r_user where usname = #{value}")
    UserModel queryUserByName(String usname);

    List<UserModel> queryUser(@Param("start") Integer start,@Param("end") Integer end);

    @Select("select  *  from  r_user  where  usname  =#{value}")
    UserModel queryUserName(@Param("usname") String usname);

    void saveMenu(MenuBean menu);

    List<MenuBean> queryMenu(@Param("powerid")Integer powerid);

    List<TreeModel> queryPowerTreeByRoleid(@Param("roleid")Integer roleid);

    List<RoleBean> queryRole();

    void deletePowerById(@Param("powerid")Integer powerid);

    void addPower(TreeModel power);

    void updatePowerById(TreeModel power);

    void deleteByRoleid(@Param("roleid")Integer roleid);

    void addRolePower(@Param("roleid")Integer roleid, @Param("ids")String[] ids);

    UserModel queryUserById(@Param("userId") Integer userId);

    void addUser(UserModel user);

    void updateUser(UserModel user);

    void deleteUserRole(@Param("usid") Integer usid);

    void addUserRole(@Param("arrId") String[] arrId,@Param("usid") Integer usid);

    @Select("select  *  from   r_user  where   usname=#{value}")
    UserModel findByName(String username);


    List<String> findPower(Integer usid);

    @Select("select  *  from  r_nav  where   pid=#{value}")
    List<TreeModel> queryTree2(int pid);

    void saveUser(ArrayList<UserModel> list);

    @Select("select count(1) from r_user")
    Integer queryUserCount();

    @Select("select * from r_user")
    List<UserModel> findUser();
}
