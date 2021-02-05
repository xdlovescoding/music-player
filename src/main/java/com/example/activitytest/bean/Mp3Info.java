package com.example.activitytest.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.activitytest.bean.SongRow.songRow;

public class Mp3Info {
    private String name;
    private String title;
    private String artist;
    private List<SongRow> songRowList;
    //private String url;
    private long duration;
    //private long size;
    //private int id;

    public Mp3Info(String name, String title, String artist, List<SongRow> songRowList, long duration) {
        this.name = name;
        this.title = title;
        this.artist = artist;
        this.songRowList = songRowList;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

//    public long getSize() {
//        return size;
//    }
//
//    public void setSize(long size) {
//        this.size = size;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<SongRow> getSongRowList() {
        return songRowList;
    }

    public void setSongRowList(List<SongRow> songRowList) {
        this.songRowList = songRowList;
    }

    @Override
    public String toString() {
        return "Mp3Info{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", songRowList=" + songRowList +
                ", duration=" + duration +
                '}';
    }

    public Mp3Info() {
    }

    public Mp3Info(String name, String title, String artist) {
        this.artist = artist;
        this.name = name;
        this.title = title;

    }

    public static List<SongRow> combine(List<String> list) {
        List<SongRow> songRowList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SongRow singleRow = songRow(list.get(i));
            if (singleRow != null) {
                songRowList.add(singleRow);
            }
        }
        return songRowList;

    }

    public String getSongName(List<String> list) {
        ReadFile.readTxtFile("C:\\Users\\DELL9642\\Desktop\\突然好想你 - 徐佳莹.lrc");
        return list.get(0).substring(10);
    }

    public String getSongWrite(List<String> list) {
        ReadFile.readTxtFile("C:\\Users\\DELL9642\\Desktop\\突然好想你 - 徐佳莹.lrc");
        return list.get(2).substring(13);
    }

    public String getArtist(List<String> list) {
        ReadFile.readTxtFile("C:\\Users\\DELL9642\\Desktop\\突然好想你 - 徐佳莹.lrc");
        return list.get(3).substring(13);
    }


}
