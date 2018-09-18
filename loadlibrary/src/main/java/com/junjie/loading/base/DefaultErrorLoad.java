package com.junjie.loading.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.junjie.loading.R;

public class DefaultErrorLoad extends BaseLoadView {

    private TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.ld_error_load_layout;
    }

    @Override
    protected void onViewCreate(Context context, View rootView) {
        tv = rootView.findViewById(R.id.error_tv);
        if (rootView.getTag() != null) {
            tv.setText((String) rootView.getTag());
        }
    }

    @Override
    public void onAttach(Context context, View rootView) {
        if (rootView.getTag() != null) {
            tv.setText((String) rootView.getTag());
        }
    }

    @Override
    public void onDetach() {

    }

}
