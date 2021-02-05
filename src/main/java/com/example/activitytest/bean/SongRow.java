package com.example.activitytest.bean;

import java.util.ArrayList;
import java.util.List;

public class SongRow {
    public long time;
    public String content;


    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "SongRow{" +
                "time=" + time +
                ", content='" + content + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public SongRow(long time,String content){
        this.content = content;
        this.time = time;
    }

    public static SongRow songRow(String str) {
        if (str.indexOf("[") != 0 || str.indexOf("]") != 9) {
            return null;
        }
        String timeString = str.substring(1, 9);
        timeString = timeString.replace('.', ':');
        //将字符串 XX:XX:XX 拆分

        String[] times = timeString.split(":");
        // mm:ss:SS
        long time = Integer.valueOf(times[0]) * 60 * 1000 +
                Integer.valueOf(times[1]) * 1000 +
                Integer.valueOf(times[2]);
        String content = str.substring(10);
        return new SongRow(time,content);

    }



}
