package com.jk.utils;

/**
 * author：wdd
 * create time:2019/10/15
 * email：
 * tel：
 */
public class ExcelUtils {
    public static boolean isXls(String fileName){
        if(fileName.matches("^.+\\.(xls)$")){
            return true;
        }else if(fileName.matches("^.+\\.(xlsx)$")){
            return false;
        }else{
            throw new RuntimeException("文件格式不对");
        }
    }
}
