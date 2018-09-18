package com.junjie.loadinglayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junjie.loading.LoadManager;
import com.junjie.loading.base.DefaultErrorLoad;
import com.junjie.loading.core.ErrorClickListener;

public class MyFragment extends Fragment implements View.OnClickListener {

    private LoadManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.myfragment_layout, container, false);
        manager = new LoadManager.Builder()
                .setRootView(inflate)
                .setOnClick(new ErrorClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
                        loadClick();
                    }
                })
                .build();
        inflate.findViewById(R.id.btn_content).setOnClickListener(this);
        inflate.findViewById(R.id.btn_error).setOnClickListener(this);
        inflate.findViewById(R.id.btn_load).setOnClickListener(this);
        return manager.getLoadLayout();
    }

    private void loadClick() {
        manager.showDefaultLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showContent();
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_content:
                manager.showContent();
                break;
            case R.id.btn_error:
                manager.showView(DefaultErrorLoad.class, "未知异常");
                break;
            case R.id.btn_load:
                loadClick();
                break;
        }
    }
}
