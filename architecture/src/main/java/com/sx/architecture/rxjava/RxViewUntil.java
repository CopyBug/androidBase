package com.sx.architecture.rxjava;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class RxViewUntil {
    public static void setClickShake(View mView, View.OnClickListener listener){

        RxView.clicks(mView).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        listener.onClick(mView);
                    }
                });
    }
}
