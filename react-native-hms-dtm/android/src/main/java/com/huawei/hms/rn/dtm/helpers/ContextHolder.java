package com.huawei.hms.rn.dtm.helpers;

import android.annotation.SuppressLint;
import android.content.Context;

public final class ContextHolder {
    @SuppressLint("StaticFieldLeak")
    private static ContextHolder instance;
    private Context context;

    private ContextHolder() {
    }

    public static synchronized ContextHolder getInstance() {
        if (instance == null) {
            instance = new ContextHolder();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
