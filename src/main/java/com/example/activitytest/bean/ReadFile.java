package com.example.activitytest.bean;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public static List<String> readTxtFile(String filePath){
        List<String> list = new ArrayList<>();
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    list.add(lineTxt);
                }
                read.close();
                System.out.print(list);
                return list;



            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String argv[]){
        String filePath = "C:\\Users\\DELL9642\\Desktop\\突然好想你 - 徐佳莹.lrc";
        readTxtFile(filePath);
    }



}

