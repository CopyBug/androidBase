package com.sx.architecture.data;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public final MutableLiveData<Boolean> showDialog = new MutableLiveData<>();
    public final ObservableField<String> dialogTv=new ObservableField<>("请稍等");
    public final ObservableField<Integer> dialog2Tv=new ObservableField<>();

    public void showDialog(){
        showDialog.postValue(true);
    }

    public void hideDialog(){
        showDialog.postValue(false);
    }


}
