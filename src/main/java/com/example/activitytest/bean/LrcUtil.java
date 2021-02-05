package com.example.activitytest.bean;

        import com.example.activitytest.R;

        import java.security.PublicKey;
        import java.util.List;

public class LrcUtil {
    public static Mp3Info parseFromFile(String path) {
        List<String> stringList = ReadFile.readTxtFile(path);
        String name =  stringList.get(1).substring(10);
        String songWrite = stringList.get(3).substring(13);
        String artist = stringList.get(4).substring(13);
        List<SongRow> songRowList = Mp3Info.combine(stringList);
        Long duration = 271778L;
        return new Mp3Info(name,songWrite,artist,songRowList, duration);
    }
    public static void main(String[] args){
        System.out.println(parseFromFile("C:\\Users\\DELL9642\\Desktop\\突然好想你 - 徐佳莹.lrc"));

    }
}