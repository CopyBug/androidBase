package com.sx.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.sx.widget.R;


public class HwTextView extends androidx.appcompat.widget.AppCompatTextView {
    public HwTextView(Context context) {
        super(context);
    }

    public HwTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HwTextView);
        if(typedArray.hasValue(R.styleable.HwTextView_hw_paint)){
            int painFlags = typedArray.getInt(0, R.styleable.HwTextView_hw_paint);
            getPaint().setFlags(painFlags| Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
        }

    }

}
