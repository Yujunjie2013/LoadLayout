package com.junjie.loading.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.junjie.loading.core.ErrorClickListener;

public abstract class BaseLoadView {
    //是否可以点击、默认可以点击
    private boolean isClickEnable = true;
    private View rootView;
    private Context context;

    public BaseLoadView() {
    }

    public BaseLoadView(boolean isClickEnable) {
        this.isClickEnable = isClickEnable;
    }

    public BaseLoadView(View rootView, Context context) {
        this.rootView = rootView;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setStateView(Context context, ErrorClickListener clickListener) {
        this.context = context;
        onCreateView(clickListener);
    }

    private View onCreateView(final ErrorClickListener clickListener) {
        int res = getLayoutId();
        //内容布局DefaultContentView时的判断
        if (res == 0 && rootView != null) {
            return rootView;
        }
        if (rootView == null) {
            rootView = LayoutInflater.from(context).inflate(res, null);
        }
        if (isClickEnable && clickListener != null) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v);
                }
            });
        }
        onViewCreate(context, rootView);
        return rootView;
    }

    public View getRootView(Object tag) {
        if (rootView != null) {
            if (tag != null) {
                rootView.setTag(tag);
            }
            return rootView;
        }
        rootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        if (tag != null) {
            rootView.setTag(tag);
        }
        onViewCreate(context, rootView);
        return rootView;
    }

    public View getRootView() {
        return getRootView(null);
    }

    public void show() {
    }

    public void hide() {
    }

    /*------以下4个方法的执行顺序是一次执行1、2、3、4、其中getLayoutId和onCreateView方法只会在创建是执行一次，
    onAttach和onDetach方法会执行多次，分别在每次显示和销毁的时候执行---------------------------*/

    /**
     * 1、布局资源id
     *
     * @return 布局id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 2、当跟布局创建，可以初始化控件，列入findViewById；setText;...
     *
     * @param context  上下文
     * @param rootView 跟布局View
     */
    protected abstract void onViewCreate(Context context, View rootView);

    /**
     * 3、当View开始显示的时候；列入：可以开始播放动画
     *
     * @param context  上下文
     * @param rootView 跟View
     */
    public abstract void onAttach(Context context, View rootView);

    /**
     * 4、释放资源；列入：可以让动画停止
     */
    public abstract void onDetach();


}
