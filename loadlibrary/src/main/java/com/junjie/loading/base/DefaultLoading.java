package com.junjie.loading.base;

import android.content.Context;
import android.view.View;

import com.junjie.loading.R;


public class DefaultLoading extends BaseLoadView {
    public DefaultLoading(boolean isClickEnable) {
        super(isClickEnable);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ld_loading_layout;
    }

    @Override
    protected void onViewCreate(Context context, View rootView) {

    }

    @Override
    public void onAttach(Context context, View rootView) {

    }

    @Override
    public void onDetach() {

    }

}
