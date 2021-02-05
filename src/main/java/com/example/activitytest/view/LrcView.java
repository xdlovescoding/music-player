package com.example.activitytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class LrcView extends View
//        implements ILrcView
{
    public final static String TAG = "LrcView";
    //三个不同模式
    public final static int DISPLAY_MODE_NORMAL = 0;
    public final static int DISPLAY_MODE_SEEK = 1;
    public final static int DISPLAY_MODE_SCALE = 2;
    // 基本变量（播放模式，歌词变量，高亮颜色，歌词颜色，字体大小，进度条颜色等)
    private int mDisplayMode = DISPLAY_MODE_NORMAL;
//    private List<LrcRow> mLrcRows;
    private int mMinSeekFiredOffset = 10;
    private int mHighlightRowColor = Color.YELLOW;
    private int mNormalRowColor = Color.WHITE;
    private int mSeekLineColor = Color.CYAN;
    private int mSeekLineTextColor = Color.CYAN;
    private int mSeekLineTextSize = 15;
    private int mMinSeekLineTextSize = 13;
    private int mMaxSeekLineTextSize = 18;
    private int mLrcFontSize = 23;
    private int mMinFontSize = 15;
    private int mMaxFontSize = 35;
    private int mPaddingY = 10;
    private int mSeekLinePaddingX = 0;
    private ILrcViewListener mLrcViewListener;//监听
    private Paint mPaint;

    public LrcView(Context context, AttributeSet attr) {
        super(context, attr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mLrcFontSize);
    }

    public void setListener(ILrcViewListener l) {
        mLrcViewListener = l;
    }

    public interface ILrcViewListener {
//        void onLrcSeeked(int position, LrcRow row);
    }
}