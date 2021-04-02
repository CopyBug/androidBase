package com.sx.widget.view;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;


public class CountDownTextView extends TextView implements Runnable {
    public volatile long startTime = 0;
    private Listning mListning;
    private HandlerThread handlerThread;
    private final int STOP_THREAD = 0x12;
    private final int STARE_THREAD = 0x13;

    public void setmListning(Listning mListning) {
        this.mListning = mListning;
    }

    /**
     * 标记是否重置了倒计控件
     */
    private boolean mFlag = true;

    /**
     * 设置倒计时总秒数
     */
    public void setTotalTime(long totalTime) {
        if (totalTime == 0 || totalTime < 0) {
            return;
        }

        this.startTime = totalTime / 1000;
        //启动倒计时
        mFlag = true;
        post(this);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

//            removeCallbacks(this);
//            post(this);
    }


    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (startTime != 0) {
            post(this);
        }

    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            while (!thread.isInterrupted()) {
                try {
                    if (startTime <= 0 || !mFlag) {
                        mFlag = false;
                        startTime = 0;
                        //停止
                        if (mListning != null) {
                            mListning.successOpen();
                        }
                        Thread.currentThread().interrupt();
                    } else {
                        //减少一秒
                        startTime -= 1;
                        mFlag = true;
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();
                }
            }
            super.run();
        }
    };
    private Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //UI
            switch (msg.what) {
                case STARE_THREAD:
                    //线程已启动
//                    setVisibility(VISIBLE);
                    break;
                case STOP_THREAD:
                    //线程已关闭
                    if (mListning != null) {
                        mListning.successOpen();
                    }
                    break;
            }
            return true;
        }
    });

    @Override
    public void run() {
        setText(CountDownUtils.initData(getContext(), startTime));
        if (startTime <= 0 || !mFlag) {
            mFlag = false;
            startTime = 0;
            //停止
            if (mListning != null) {
                mListning.successOpen();
            }
//            setVisibility(INVISIBLE);
            uiHandler.removeCallbacks(this);
//            removeCallbacks(this);
        } else {

            //减少一秒
            startTime -= 1;
            mFlag = true;
            uiHandler.postDelayed(this, 1000);
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

        super.onWindowFocusChanged(hasWindowFocus);
    }

    public interface Listning {
        void successOpen();
    }
}
