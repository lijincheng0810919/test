package com.jk.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.model.*;
import com.jk.service.UserService;
import com.jk.utils.ExcelImEx;
import com.jk.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Reference(timeout = 600000)
    private UserService userService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;




    @RequestMapping("queryTree")
    @ResponseBody
    public List<TreeModel> queryTree(){
        Subject subject = SecurityUtils.getSubject();
        UserModel user= (UserModel) subject.getPrincipal();
        return  userService.queryTree(user.getUsid());
    }

  /*  @RequestMapping("findUserList")
    @ResponseBody
    public List<UserBean> findUserList(){
        return userService.findUserList();
    }

    @RequestMapping("querySexList")
    @ResponseBody
    public List<DictionaryBean> querySexList(){
        return userService.querySexList();
    }

    @RequestMapping("queryStatusList")
    @ResponseBody
    public List<DictionaryBean> queryStatusList(){
        return userService.queryStatusList();
    }
*/
   /* @RequestMapping("addUser")
    @ResponseBody
    public void addUser2(UserBean userBean){
        userService.addUser2(userBean);
    }
*/

    @RequestMapping("login")
    @ResponseBody
    //shiro 不支持分布式
    public Map login(String usname, String uspass, int code, HttpSession session){
        //判断验证码
        HashMap<Object, Object> hashMap = new HashMap<>();
        Object imgcode = session.getAttribute("imgcode");
        if (imgcode == null){
            hashMap.put("code",1);
            hashMap.put("msg","验证码错误！！！");
            return hashMap;
        }

        int anInt = Integer.parseInt(imgcode.toString());
        if (anInt != code){
            hashMap.put("code",1);
            hashMap.put("msg","验证码错误！！！");
            return hashMap;
        }

        //获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(usname, uspass);

        String msg = "";
        try {
            //登录认证
            subject.login(usernamePasswordToken);
            //获取session对象
            Session session1 = subject.getSession();
            //查询账号是否存在
            UserModel userBean = userService.queryUserByName(usname);
            //将账号存到session中
            session1.setAttribute(session1.getId(),userBean);

            hashMap.put("code",0);
            hashMap.put("loginName",usname);
            hashMap.put("msg","登录成功！！！");
            return hashMap;
        }catch (UnknownAccountException e) {
            msg = "账号不存在";
        } catch (IncorrectCredentialsException e) {
            msg = "密码不正确：";
        } catch (AuthenticationException e) {
            msg="用户验证失败";
        }
        hashMap.put("code",1);
        hashMap.put("msg",msg);
        return hashMap;
    }

    @Bean(value = "userService")
    public UserService getUserService() {
        return userService;
    }



    //导出excel
    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response,String filename) throws IOException, IllegalAccessException {
        //从数据库查询用户列表
        List<UserModel> list = userService.findUser();
        String[] titles = {"id","用户名","密码","角色"};
        ExcelImEx.exportExcel(response,filename, Collections.singletonList(list),titles,"用户信息",UserModel.class);
    }


    /***
     * poi导出百万数据
     */
    @RequestMapping("exportlUserExcel2")
    @ResponseBody
    public void exportlUserExcel2(HttpServletResponse response) throws IOException {
        //数量
        Integer count = userService.queryUserCount();
        //计算有多少页
        Integer page = count%10000 == 0 ? count/10000 : count/10000 + 1;

        List<UserModel> list = userService.findUser();

        //计算每页几条
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(5000);

        for (int i = 0; i < page; i++) {
            Sheet sheet = sxssfWorkbook.createSheet("用户信息" + (i + 1));
            //行下标
            Row row = sheet.createRow(0);
            System.out.println(row);
            //列下标
            Cell cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("账号");
            cell = row.createCell(2);
            cell.setCellValue("密码");
            List<UserModel> userlimit = userService.queryUserLimit((i+1),10000);
            for (int j = 0; j < userlimit.size(); j++) {
                row = sheet.createRow(j+1);
                cell = row.createCell(0);
                cell.setCellValue(userlimit.get(j).getId());
                cell = row.createCell(1);
                cell.setCellValue(userlimit.get(j).getUsname());
                cell = row.createCell(2);
                cell.setCellValue(userlimit.get(j).getUspass());
            }
        }

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("用户信息.xlsx","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        sxssfWorkbook.write(outputStream);
    }



    /*

    */
/*
    * poi导出
    * *//*

    @RequestMapping("exportContentList")
    public void exportContentList(HttpServletResponse response){
        //获取数据信息
        List<UserModel> userList = userService.queryUser();
        //标题
        String title = "基本信息";
        String[] rowsName = new String[]{"序号","名字","密码"};

        //定义数据集合
        List<Object[]> list = new ArrayList<>();

        Object[] obj  = null;

        for (int i = 0; i < userList.size(); i++) {
            obj = new Object[rowsName.length];
            obj[0] = userList.get(i).getUsid();
            obj[1] = userList.get(i).getUsname();
            obj[2] = userList.get(i).getUspass();
            list.add(obj);
        }

        ExportExcel exportExcel = new ExportExcel(title, rowsName, list, response);

        try {
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * 导入
     * @return
     */
    @RequestMapping("importExcel")
    @ResponseBody
    public void importExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();

        boolean xls = ExcelUtils.isXls(originalFilename);
        Workbook workbook = null;
        if (xls){
             workbook = new HSSFWorkbook(inputStream);
        }else{
             workbook = new XSSFWorkbook(inputStream);
        }
        Sheet sheetAt = workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        ArrayList<UserModel> list = new ArrayList<>();

        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheetAt.getRow(i + 1);
            String a = row.getCell(0).getStringCellValue();
            String b = row.getCell(1).getStringCellValue();
            String c = row.getCell(2).getStringCellValue();
            UserModel userModel = new UserModel();
            userModel.setUsname(b);
            userModel.setUspass(c);
            list.add(userModel);
        }
        userService.saveUser(list);
    }




    @RequestMapping("queryUser")
    @ResponseBody
    public HashMap<String,Object> queryUser(Integer page,Integer rows){
        return   userService.queryUser(page,rows);
    }


    @RequestMapping("saveMenu")
    @ResponseBody
    public void saveMenu(MenuBean menu){
        userService.saveMenu(menu);
    }


    @RequestMapping("queryMenu")
    @ResponseBody
    public List<MenuBean> queryMenu(Integer powerid){
        return userService.queryMenu(powerid);
    }




    @RequestMapping("queryPowerTree")
    @ResponseBody

    public List<TreeModel> queryPowerTree(Integer roleid){
        return userService.queryPowerTree(roleid);
    }

   //ROLE
    @RequestMapping("queryRole")
    @ResponseBody
    public List<RoleBean> queryRole(){
        return userService.queryRole();
    }


    @RequestMapping("deletePowerById")
    @ResponseBody
    @RequiresPermissions("user:deletePowerById")
    public void deletePowerById(Integer powerid){
        userService.deletePowerById(powerid);
    }


    @RequestMapping("addPower")
    @ResponseBody
    @RequiresPermissions("user:addPower")
    public void addPower(TreeModel power){
        userService.addPower(power);
    }


    @RequestMapping("updatePowerById")
    @ResponseBody
    @RequiresPermissions("user:updatePowerById")
    public void updatePowerById(TreeModel power){
        userService.updatePowerById(power);
    }

    @RequestMapping("saveRolePower")
    @ResponseBody
    @RequiresPermissions("user:saveRolePower")
    public void saveRolePower(Integer roleid,String[] ids){

        userService.saveRolePower(roleid,ids);
    }

    @RequestMapping("queryUserById")
    @ResponseBody
    public UserModel queryUserById(Integer userId){
        return userService.queryUserById(userId);
    }

    @RequestMapping("addUser")
    @ResponseBody
    @RequiresPermissions("user:addUser")//标识这个方法需要权限
    public void addUser(UserModel user){
        userService.addUser(user);
    }


}
