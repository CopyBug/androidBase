package com.sx.architecture.rxjava;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.sx.architecture.tools.RomUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

public class RxPermissionReq {
    private static RxPermissionReq rxPermissionReq;

    public static RxPermissionReq getInstance() {
        if (rxPermissionReq == null) {
            synchronized (RxPermissionReq.class) {
                if (rxPermissionReq == null) {
                    rxPermissionReq = new RxPermissionReq();
                }
            }
        }
        return rxPermissionReq;
    }


    //读写
    public static final String PRE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //相机
    public static final String PRE_CAMERA = Manifest.permission.CAMERA;
    //定位
    public static final String PRE_LOCATION1 = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PRE_LOCATION2 = Manifest.permission.ACCESS_FINE_LOCATION;
    //录音
    public static final String PRE_RECORD = Manifest.permission.RECORD_AUDIO;
    //手机
    public static final String PRE_PHONE = Manifest.permission.READ_PHONE_STATE;
    //拨打电话
    public static final String PRE_CALL = Manifest.permission.CALL_PHONE;


    public Observable<Permission> reqPermission(Activity act, String... permission) {
        return new RxPermissions(act).requestEachCombined(permission);
    }


    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        // 有一个为true，就代表定位服务已经打开
        if (gps && network) {
            return true;
        }
        return false;
    }

    public void startPermissionPage(Context mContext) {
        Intent mIntent=null;
        if (RomUtils.isMiui()) {
            mIntent=RomUtils.MiuiPermission(mContext);
        } else if (RomUtils.isMeizu()) {
            mIntent=RomUtils.MeiZuPermission(mContext);
        } else if (RomUtils.isHuawei()) {
            mIntent=RomUtils.HuaWeiPermission(mContext);
        } else if (RomUtils.isQihoo()) {
            mIntent=RomUtils.QihooPermission(mContext);
        } else if(RomUtils.isSony()){
            mIntent=RomUtils.SonyPermission(mContext);
        }else if(RomUtils.isOppo()){
            mIntent=RomUtils.OppoPermission(mContext);
        }else if(RomUtils.isLg()){
            mIntent=RomUtils.LgPermission(mContext);
        }else{
            mIntent=RomUtils.applicationInfo(mContext);
        }
        mContext.startActivity(mIntent);
    }



}
