package com.magarex.bigchef.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<VDB extends ViewDataBinding> extends DaggerFragment {

    private VDB dataBinding;

    @LayoutRes
    protected abstract int provideLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, provideLayout(), container, false);
        return dataBinding.getRoot();
    }

    protected VDB getDataBinding() {
        return dataBinding;
    }

}
