package com.junjie.loading.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.junjie.loading.LoadManager;

public class LoadUtil {
    public static LoadManager.ViewParam getViewParams(@NonNull Object object) {
        View rootView;
        Context context;
        ViewGroup rootParentView;
        if (object instanceof Activity) {
            Activity activity = (Activity) object;
            context = activity;
            rootParentView = activity.findViewById(android.R.id.content);
            //这个得到的就是setContentView中的跟布局
            rootView = rootParentView != null ? rootParentView.getChildAt(0) : null;
        } else if (object instanceof View) {
            rootView = (View) object;
            context = rootView.getContext();
            rootParentView = (ViewGroup) rootView.getParent();
        } else {
            throw new IllegalArgumentException("The object must be View");
        }
        if (rootParentView != null) {
            rootParentView.removeView(rootView);
        }
        return new LoadManager.ViewParam(rootView, context, rootParentView);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
