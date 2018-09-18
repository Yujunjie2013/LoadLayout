package com.junjie.loading.base;

import android.content.Context;
import android.view.View;

public class DefaultContentView extends BaseLoadView {

    public DefaultContentView(View rootView, Context context) {
        super(rootView, context);
    }

    @Override
    protected int getLayoutId() {
        return 0;
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

    @Override
    public void show() {
        getRootView(null).setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        getRootView(null).setVisibility(View.INVISIBLE);
    }

}
