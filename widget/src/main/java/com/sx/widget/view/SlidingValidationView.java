package com.sx.widget.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SlidingValidationView extends View {
    public String centerText="请在此处滑动";
    public int background=0xff2D2D2D;
    public int slideColor=0xffFF5B4D;
    public Drawable slideIcon;

    public SlidingValidationView(Context context) {
        super(context);
    }

    public SlidingValidationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingValidationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
