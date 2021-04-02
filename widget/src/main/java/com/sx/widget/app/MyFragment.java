package com.sx.widget.app;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.sx.architecture.data.DataBindingConfig;
import com.sx.architecture.data.MyViewModel;
import com.sx.architecture.rxjava.RxBus;
import com.sx.architecture.ui.moudle.BaseActivity;
import com.sx.architecture.ui.moudle.BaseDialog;
import com.sx.architecture.ui.moudle.BaseFragment;
import com.sx.widget.R;
import com.sx.widget.dialog.WaitDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class MyFragment<D extends ViewDataBinding, A extends BaseActivity> extends BaseFragment<D> {
    private List<Disposable> rxBusManager;
    private View view;

    public <T> void registerRxBus(Class<T> mClass, Consumer<T> consumer) {
        if (rxBusManager == null) {
            rxBusManager = new ArrayList<>();
        }
        Disposable subscribe = RxBus.getDefault().toObservable(mClass)
                .subscribe(consumer);
        rxBusManager.add(subscribe);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        initWaitDialog();
        bindSkeleton();
        return view;
    }

    @Override
    public void onDestroy() {
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

    public A getMyActivity() {
        return (A) getActivity();
    }

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

    /**
     * 显示加载对话框
     */
    public void showDialog(String tv) {
        mDialogTotal++;
        postDelayed(() -> {
            if (mDialogTotal > 0 && !getMyActivity().isFinishing()) {
                if (mDialog == null) {
                    mDialog = new WaitDialog.Builder(getContext())
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

        if (mDialogTotal == 0 && mDialog != null && mDialog.isShowing() && !getMyActivity().isFinishing()) {
            mDialog.dismiss();
        }
    }

    protected void initWaitDialog() {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        if (dataBindingConfig != null) {
            MyViewModel stateViewMode = dataBindingConfig.getStateViewModel();
            if (stateViewMode != null) {
                stateViewMode.showDialog.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
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
                                if (aBoolean) {
                                    showDialog(viewModel.dialogTv.get());
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

    public void bindSkeleton() {

    }

    /*public Map<Integer, RecyclerViewSkeletonScreen> skeletonMap = new HashMap<>();

    public void initSkeletonRecyclerView(int recyID, RecyclerView.Adapter adapter, int layout, int count) {
        RecyclerViewSkeletonScreen show = Skeleton.bind(this.<RecyclerView>findViewById(recyID))
                .adapter(adapter)
                .shimmer(true)
                .color(R.color.color_skeleton_change)
                .count(count)//default count is 10
                .load(layout)
                .show();
        skeletonMap.put(recyID, show);
    }

    public void hideSkeletonScreen(int recyId,int delayMillis) {
        if(skeletonMap.containsKey(recyId)){
            RecyclerViewSkeletonScreen recyclerViewSkeletonScreen = skeletonMap.get(recyId);
            skeletonMap.remove(recyId);
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerViewSkeletonScreen.hide();
                }
            },delayMillis);
        }
    }*/




}