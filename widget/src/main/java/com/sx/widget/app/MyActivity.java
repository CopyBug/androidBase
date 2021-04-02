package com.sx.widget.app;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import com.sx.architecture.data.DataBindingConfig;
import com.sx.architecture.data.MyViewModel;
import com.sx.architecture.rxjava.RxBus;
import com.sx.architecture.ui.moudle.BaseActivity;
import com.sx.architecture.ui.moudle.BaseDialog;
import com.sx.widget.dialog.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class MyActivity<B extends ViewDataBinding> extends BaseActivity<B> {

    public String TAG = this.getClass().getSimpleName();
    private List<Disposable> rxBusManager;
    /**
     * 加载对话框
     */
    private BaseDialog mDialog;
    /**
     * 对话框数量
     */
    private int mDialogTotal;

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    public <T> void registerRxBus(Class<T> mClass, Consumer<T> consumer) {
        if (rxBusManager == null) {
            rxBusManager = new ArrayList<>();
        }
        Disposable subscribe = RxBus.getDefault().toObservable(mClass)
                .subscribe(consumer);
        rxBusManager.add(subscribe);
    }


    /**
     * 显示加载对话框
     */
    public void showDialog(String tv) {
        mDialogTotal++;
        postDelayed(() -> {
            if (mDialogTotal > 0 && !isFinishing()) {
                if (mDialog == null) {
                    mDialog = new WaitDialog.Builder(this)
                            .setMessage(tv)
                            .create();
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        }, 300);
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }

        if (mDialogTotal == 0 && mDialog != null && mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isShowDialog()) {
            hideDialog();
        }
        if (rxBusManager != null) {
            for (Disposable disposable : rxBusManager) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
            rxBusManager = null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWaitDialog();
        initData();
        initView();
        Log.i(TAG, "onCreate");
    }

    protected void initWaitDialog() {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        if (dataBindingConfig != null) {
            MyViewModel stateViewMode = dataBindingConfig.getStateViewModel();
            if (stateViewMode != null) {
                stateViewMode.showDialog.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Log.i(TAG, "onChanged: " + aBoolean);
                        if (aBoolean) {
                            showDialog(stateViewMode.dialogTv.get());
                        } else {
                            hideDialog();
                        }
                    }
                });
            }
            SparseArray bindingParams = dataBindingConfig.getBindingParams();
            if (bindingParams != null) {
                for (int i = 0, length = bindingParams.size(); i < length; i++) {
                    Object value = bindingParams.valueAt(i);
                    if (value instanceof MyViewModel) {
                        MyViewModel viewModel = (MyViewModel) value;
                        viewModel.showDialog.observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                Log.i(TAG, "onChanged: " + aBoolean);
                                if (aBoolean) {
                                    if(viewModel.dialog2Tv.get()!=null){
                                        showDialog(getString(viewModel.dialog2Tv.get()));
                                    }else{
                                        showDialog(viewModel.dialogTv.get());
                                    }

                                } else {
                                    hideDialog();
                                }
                            }
                        });

                    }
                }
            }

        }
    }

    public abstract void initView();

    public abstract void initData();

  /*  private ViewSkeletonScreen skeletonBind;

    public void bindSkeleton(int loadLayout) {
        skeletonBind = Skeleton.bind(getBinding().getRoot())
                .shimmer(false)
                .color(R.color.color_skeleton_change)
                .load(loadLayout)
                .show();
    }
    public void bindSkeleton(int rootLayout,int loadLayout) {
        View viewById = findViewById(rootLayout);
        skeletonBind = Skeleton.bind(viewById)
                .shimmer(false)
                .color(R.color.color_skeleton_change)
                .load(loadLayout)
                .show();
    }

    public void hideSkeleton() {
        if (skeletonBind != null) {
            skeletonBind.hide();
            skeletonBind=null;
        }
    }*/

    public static Class getMyClass() {
        return MyActivity.class;
    }
}
