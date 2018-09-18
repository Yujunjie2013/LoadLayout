package com.junjie.loadinglayout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.junjie.loading.LoadManager;
import com.junjie.loading.core.ErrorClickListener;

public class MainActivity extends AppCompatActivity {

    private LoadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new LoadManager.Builder()
                .setRootView(this)
                .setOnClick(new ErrorClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                        manager.showDefaultLoading();
                        click();
                    }
                })
                .build();
        manager.showDefaultError();
        findViewById(R.id.skip_frg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity.startA(MainActivity.this);
            }
        });
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.showDefaultError();
            }
        });
    }

    public void click() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showContent();
            }
        }, 2000);
    }
}
