package com.sx.architecture.data;

import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

public class DataBindingConfig {
    private final int layout;

    private final int vmVariableId;

    private final MyViewModel stateViewModel;

    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, int vmVariableId, MyViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }
    public DataBindingConfig(int layout) {
        this.layout = layout;
        this.vmVariableId = 0;
        this.stateViewModel = null;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public MyViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(int variableId, Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
