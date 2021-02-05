package com.example.activitytest.activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.example.activitytest.bean.Mp3Info;
import com.example.activitytest.bean.SongRow;

import org.w3c.dom.Text;

public class LrcAdapter extends BaseAdapter {
    private Mp3Info dataSource;
    private int itemHeight;


    public LrcAdapter(Mp3Info info) {
        super();
        dataSource = info;
    }

    @Override
    public int getCount() {
        return dataSource.getSongRowList() == null ? 0 : dataSource.getSongRowList().size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.getSongRowList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static final String TAG = "LrcAdapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
        }
        final TextView view = (TextView) convertView;

        SongRow songRow = dataSource.getSongRowList().get(position);
        Log.d(TAG, "getView: " + position + " " + songRow.content + ", currentPos: " + currentPos + ", size: " + dataSource.getSongRowList().size());
        view.setText(songRow.content);
        if (position == currentPos) {
            view.setTextColor(Color.BLACK);
            //view.setBackgroundColor(Color.BLACK);
        } else {
            view.setTextColor(Color.GRAY);
            //view.setBackgroundColor(Color.YELLOW);
        }

        if (itemHeight == 0) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    itemHeight = view.getHeight();
                }
            });
        }
        return view;
    }

    public int getItemHeight() {
        Log.d(TAG, "run: " + itemHeight);
        return itemHeight;
    }


    private int currentPos;

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        //Log.d(TAG, "setCurrentPos: " + currentPos);
        if (this.currentPos != currentPos) {
            this.currentPos = currentPos;
            notifyDataSetChanged();
        }
    }


}
